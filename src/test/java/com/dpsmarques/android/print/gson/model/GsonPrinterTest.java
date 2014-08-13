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

import com.dpsmarques.android.print.model.Printer;
import com.google.gson.JsonObject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class GsonPrinterTest extends TestCase {

    @Test
    public void givenNoNameWhenGetNameThenNullReturned() {
        final GsonPrinter printer = new GsonPrinter(new JsonObject());
        assertNull(printer.getName());
    }

    @Test
    public void givenNoNameWhenGetIDThenNullReturned() {
        final GsonPrinter printer = new GsonPrinter(new JsonObject());
        assertNull(printer.getId());
    }

    @Test
    public void givenNoNameWhenGetDescriptionThenNullReturned() {
        final GsonPrinter printer = new GsonPrinter(new JsonObject());
        assertNull(printer.getDescription());
    }

    @Test
    public void givenCompleteJsonWhenConstructedThenGettersReturnValues() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Printer.ID, "1");
        jsonObject.addProperty(Printer.NAME, "2");
        jsonObject.addProperty(Printer.DESCRIPTION, "3");
        final GsonPrinter printer = new GsonPrinter(jsonObject);
        assertEquals("1", printer.getId());
        assertEquals("2", printer.getName());
        assertEquals("3", printer.getDescription());

        assertSame(jsonObject, printer.mJsonObject);
    }

}