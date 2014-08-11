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
package com.dpsmarques.android.print.model;

import com.dpsmarques.android.print.gson.model.GsonModel;

/**
 * Defines a basic interface with accessor methods for a printer.
 *
 * @see com.dpsmarques.android.print.GoogleCloudPrint#getPrinters(String)
 * @see com.dpsmarques.android.print.gson.model.GsonPrinter
 * @see com.dpsmarques.android.print.gson.GsonPrinterOperator
 */
public interface Printer {

    public String getId();

    public String getName();

    public String getDescription();
}
