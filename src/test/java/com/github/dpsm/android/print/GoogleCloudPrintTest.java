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
package com.github.dpsm.android.print;

import com.github.dpsm.android.print.GoogleCloudPrint;
import com.github.dpsm.android.print.GoogleCloudPrintApi;
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

import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import rx.Observable;
import rx.Observer;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class GoogleCloudPrintTest extends TestCase {

    @Test
    public void givenGoogleCouldPrintApiWhenInvokedTokenFormatted() {
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);

        cloudPrint.getPrinters("aaa");
        Mockito.verify(api, Mockito.never()).getPrinters("aaa");
        Mockito.verify(api).getPrinters("Bearer aaa");

        cloudPrint.getPrinter("bbb", "ID", false, null);
        Mockito.verify(api, Mockito.never()).getPrinter("bbb", null, false, null);
        Mockito.verify(api).getPrinter("Bearer bbb", "ID", false, null);

        cloudPrint.getJobs("ccc", "ID", null, null);
        Mockito.verify(api, Mockito.never()).getJobs("ccc", null, null, null);
        Mockito.verify(api).getJobs("Bearer ccc", "ID", null, null);

        final TypedFile typedFile = Mockito.mock(TypedFile.class);
        Mockito.when(typedFile.length()).thenReturn(1L);

        cloudPrint.submitPrintJob("ddd", "ID", "something", "something", typedFile);
        Mockito.verify(api, Mockito.never()).submitPrintJob("ddd", "ID", "something", "something", typedFile);
        Mockito.verify(api).submitPrintJob("Bearer ddd", "ID", "something", "something", typedFile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetPrintersWithNoToken (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getPrinters(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetPrintersWithEmptyToken (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getPrinters("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetPrinterWithNoToken (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getPrinter(null, null, false, null);
    }

    @Test(expected = IllegalArgumentException.class)
     public void givenGoogleCouldPrintCanNotGetPrinterWithEmptyToken (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getPrinter("", null, false, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetPrinterWithNoPrinterID (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getPrinter("token", null, false, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetPrinterWithEmptyPrinterID (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getPrinter("token", "", false, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetJobsWithNullToken (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getJobs(null, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetJobsWithEmptyToken (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getJobs("", null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetJobsWithNullPrinterID (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getJobs("token", null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenGoogleCouldPrintCanNotGetJobsWithEmptyPrinterID (){
        final GoogleCloudPrintApi api = Mockito.mock(GoogleCloudPrint.class);
        final GoogleCloudPrint cloudPrint = new GoogleCloudPrint(api);
        cloudPrint.getJobs("token", "", null, null);
    }

}