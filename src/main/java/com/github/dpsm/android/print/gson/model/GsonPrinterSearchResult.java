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
package com.github.dpsm.android.print.gson.model;

import com.github.dpsm.android.print.model.Printer;
import com.github.dpsm.android.print.model.PrinterSearchResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Gson implementation of PrinterSearchResult.
 */
public class GsonPrinterSearchResult extends GsonModel implements PrinterSearchResult {

    private final List<GsonPrinter> printers;

    public GsonPrinterSearchResult(final JsonObject jsonObject) {
        super(jsonObject);

        printers = new LinkedList<GsonPrinter>();
        final JsonArray printersArray = mJsonObject.getAsJsonArray(PRINTERS);
        if (printersArray != null) {
            final Iterator<JsonElement> iterator = printersArray.iterator();
            while (iterator.hasNext()) {
                printers.add(new GsonPrinter(iterator.next().getAsJsonObject()));
            }
        }
    }

    @Override
    public List<Printer> getPrinters() {
        return new ArrayList<Printer>(printers);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PrinterSearchResult result = (PrinterSearchResult) o;

        if (getPrinters() != null ? !getPrinters().equals(result.getPrinters()) : result.getPrinters()!= null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getPrinters() != null ? getPrinters().hashCode() : 0;
    }

}
