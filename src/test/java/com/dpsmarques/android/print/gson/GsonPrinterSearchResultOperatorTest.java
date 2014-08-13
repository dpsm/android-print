/*
 * Copyright (C) 2014 David Marques.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dpsmarques.android.print.gson;

import com.dpsmarques.android.print.gson.model.GsonPrinterSearchResult;
import com.dpsmarques.android.print.model.Printer;
import com.dpsmarques.android.print.model.PrinterSearchResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedString;
import rx.Observable;
import rx.Observer;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class GsonPrinterSearchResultOperatorTest extends TestCase {

    @Test
    public void givenResponseThenSearchResultEmitted() {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray array = new JsonArray();

        final JsonObject jsonObject1 = new JsonObject();
        array.add(jsonObject1);
        final JsonObject jsonObject2 = new JsonObject();
        array.add(jsonObject2);

        jsonObject.add(PrinterSearchResult.PRINTERS, array);

        final Response response = new Response("", 200, "OK", new ArrayList<Header>(), new TypedString(jsonObject.toString()));

        final GsonPrinterSearchResultObserver observer = Mockito.mock(GsonPrinterSearchResultObserver.class);
        Observable.from(response).lift(new GsonPrinterSearchResultOperator()).subscribe(observer);

        final ArgumentCaptor<GsonPrinterSearchResult> captor = ArgumentCaptor.forClass(GsonPrinterSearchResult.class);

        Mockito.verify(observer, Mockito.never()).onError(Matchers.any(Throwable.class));
        Mockito.verify(observer).onNext(captor.capture());
        Mockito.verify(observer).onCompleted();

        assertNotNull(captor.getValue());
        assertEquals(2, captor.getValue().getPrinters().size());
    }

    @Test
    public void givenResponseWithFailureWhenSubscribedThenOnError() {
        final Response response = new Response("", 401, "OK", new ArrayList<Header>(), new TypedString(""));

        final GsonPrinterSearchResultObserver observer = Mockito.mock(GsonPrinterSearchResultObserver.class);
        Observable.from(response).lift(new GsonPrinterSearchResultOperator()).subscribe(observer);

        Mockito.verify(observer).onError(Matchers.any(Throwable.class));
        Mockito.verify(observer, Mockito.never()).onNext(Matchers.any(GsonPrinterSearchResult.class));
        Mockito.verify(observer, Mockito.never()).onCompleted();
    }

    private static abstract class GsonPrinterSearchResultObserver implements Observer<GsonPrinterSearchResult> {

    }

}