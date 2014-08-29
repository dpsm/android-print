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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Gson implementation of Printer.
 */
public class GsonPrinter extends GsonModel implements Printer {

    public GsonPrinter(final JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public String getId() {
        final JsonElement value = mJsonObject.get(ID);
        return value != null ? value.getAsString() : null;
    }

    @Override
    public String getName() {
        final JsonElement value = mJsonObject.get(NAME);
        return value != null ? value.getAsString() : null;
    }

    @Override
    public String getDescription() {
        final JsonElement value = mJsonObject.get(DESCRIPTION);
        return value != null ? value.getAsString() : null;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final GsonPrinter that = (GsonPrinter) o;
        final String id = getId();
        final String otherId = ((GsonPrinter) o).getId();

        if (id != null ? !id.equals(otherId) : otherId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
