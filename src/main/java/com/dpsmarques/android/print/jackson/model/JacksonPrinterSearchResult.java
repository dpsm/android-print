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
package com.dpsmarques.android.print.jackson.model;

import com.dpsmarques.android.print.model.Printer;
import com.dpsmarques.android.print.model.PrinterSearchResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Jackson based implementation of the PrinterSearchResult interface.
 */
public class JacksonPrinterSearchResult extends JacksonModel implements PrinterSearchResult {

    private final List<JacksonPrinter> printers;

    public JacksonPrinterSearchResult(final ObjectNode objectNode) {
        super(objectNode);

        final JsonNode node = mObjectNode.get("printers");
        printers = new ArrayList<JacksonPrinter>(node.size());
        for(JsonNode child : node) {
            printers.add(new JacksonPrinter((ObjectNode) child));
        }
    }

    @Override
    public List<Printer> getPrinters() {
        return new ArrayList<Printer>(printers);
    }

}
