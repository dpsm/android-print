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
package com.github.dpsm.android.print.gson;

import com.github.dpsm.android.print.gson.model.GsonPrinter;
import com.github.dpsm.android.print.model.Printer;
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

import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedString;
import rx.Observable;
import rx.Observer;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class GsonResultOperatorTest extends TestCase {

    @Test
    public void givenResponseThenSearchResultEmitted() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Printer.ID, "1");
        jsonObject.addProperty(Printer.NAME, "2");
        jsonObject.addProperty(Printer.DESCRIPTION, "3");

        final Response response = new Response("", 200, "OK", new ArrayList<Header>(), new TypedString(jsonObject.toString()));

        final GsonPrinterObserver observer = Mockito.mock(GsonPrinterObserver.class);
        Observable.from(response).lift(new GsonPrinterOperator()).subscribe(observer);

        final ArgumentCaptor<GsonPrinter> captor = ArgumentCaptor.forClass(GsonPrinter.class);

        Mockito.verify(observer, Mockito.never()).onError(Matchers.any(Throwable.class));
        Mockito.verify(observer).onNext(captor.capture());
        Mockito.verify(observer).onCompleted();

        assertNotNull(captor.getValue());
        assertEquals("1", captor.getValue().getId());
        assertEquals("2", captor.getValue().getName());
        assertEquals("3", captor.getValue().getDescription());
    }

    @Test
    public void givenResponseWithFailureWhenSubscribedThenOnError() {
        final Response response = new Response("", 401, "OK", new ArrayList<Header>(), new TypedString(""));

        final GsonPrinterObserver observer = Mockito.mock(GsonPrinterObserver.class);
        Observable.from(response).lift(new GsonPrinterOperator()).subscribe(observer);

        Mockito.verify(observer).onError(Matchers.any(Throwable.class));
        Mockito.verify(observer, Mockito.never()).onNext(Matchers.any(GsonPrinter.class));
        Mockito.verify(observer, Mockito.never()).onCompleted();
    }

    private static abstract class GsonPrinterObserver implements Observer<GsonPrinter> {

    }

}