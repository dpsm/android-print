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
package com.dpsmarques.android.print.gson.model;

import com.dpsmarques.android.print.model.PrinterSearchResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class GsonPrinterSearchResultTest extends TestCase {

    @Test
    public void givenEmptyJsonWhenGetNameThenNullReturned() {
        final GsonPrinterSearchResult printer = new GsonPrinterSearchResult(new JsonObject());
        assertNotNull(printer.getPrinters());
        assertTrue(printer.getPrinters().isEmpty());
    }

    @Test
    public void givenEmptyPrintersWhenGetNameThenNullReturned() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add(PrinterSearchResult.PRINTERS, new JsonArray());

        final GsonPrinterSearchResult printer = new GsonPrinterSearchResult(jsonObject);
        assertNotNull(printer.getPrinters());
        assertTrue(printer.getPrinters().isEmpty());
    }

    @Test
    public void givenPrintersWhenGetPrintersThenPrintersHaveJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray array = new JsonArray();

        final JsonObject jsonObject1 = new JsonObject();
        array.add(jsonObject1);
        final JsonObject jsonObject2 = new JsonObject();
        array.add(jsonObject2);

        jsonObject.add(PrinterSearchResult.PRINTERS, array);

        final GsonPrinterSearchResult printer = new GsonPrinterSearchResult(jsonObject);
        assertNotNull(printer.getPrinters());

        assertNotNull(printer.getPrinters().get(0));
        assertSame(jsonObject1, ((GsonPrinter)printer.getPrinters().get(0)).getJsonObject());

        assertNotNull(printer.getPrinters().get(1));
        assertSame(jsonObject2, ((GsonPrinter)printer.getPrinters().get(1)).getJsonObject());
    }

}