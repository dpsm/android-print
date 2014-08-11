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
import com.google.gson.Gson;

/**
 * Extends GsonResultOperator targeting the GsonPrinterSearchResult class.
 */
public class GsonPrinterSearchResultOperator extends GsonResultOperator<GsonPrinterSearchResult> {

    public GsonPrinterSearchResultOperator(final Gson gson) {
        super(gson, GsonPrinterSearchResult.class);
    }

    public GsonPrinterSearchResultOperator() {
        super(GsonPrinterSearchResult.class);
    }
}
