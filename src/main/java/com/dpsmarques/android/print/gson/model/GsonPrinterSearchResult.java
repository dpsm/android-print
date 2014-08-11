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
import com.dpsmarques.android.print.model.PrinterSearchResult;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Gson implementation of PrinterSearchResult.
 */
public class GsonPrinterSearchResult implements PrinterSearchResult, GsonModel {

    List<GsonPrinter> printers;

    JsonObject mJsonObject;

    @Override
    public List<Printer> getPrinters() {
        return new ArrayList<Printer>(printers);
    }

    @Override
    public JsonObject getJsonObject() {
        return mJsonObject;
    }

    @Override
    public void setJsonObject(final JsonObject mJsonObject) {
        this.mJsonObject = mJsonObject;
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
