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
package com.github.dpsm.android.print.jackson.model;

import com.github.dpsm.android.print.model.Printer;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The Jackson based implementation of the Printer interface.
 */
public class JacksonPrinter extends JacksonModel implements Printer {

    public JacksonPrinter(final ObjectNode objectNode) {
        super(objectNode);
    }

    @Override
    public String getId() {
        return mObjectNode.get(ID).asText();
    }

    @Override
    public String getName() {
        return mObjectNode.get(NAME).asText();
    }

    @Override
    public String getDescription() {
        return mObjectNode.get(DESCRIPTION).asText();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final String id = getId();
        final String otherID = ((JacksonPrinter) o).getId();
        if (id != null ? !id.equals(otherID) : otherID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return getName();
    }
}
