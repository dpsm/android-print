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
package com.github.dpsm.android.print.jackson;

import com.github.dpsm.android.print.jackson.model.JacksonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Extends JacksonResultOperator targeting the JacksonPrinter class.
 */
public class JacksonPrinterOperator extends JacksonResultOperator<JacksonPrinter> {

    public JacksonPrinterOperator(final ObjectMapper mapper) {
        super(mapper, JacksonPrinter.class);
    }

    public JacksonPrinterOperator() {
        super(JacksonPrinter.class);
    }
}
