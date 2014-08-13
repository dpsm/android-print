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

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This abstract class defines a model built from Jackson.
 */
public abstract class JacksonModel {

    protected final ObjectNode mObjectNode;

    protected JacksonModel(final ObjectNode objectNode) {
        mObjectNode = objectNode;
    }

    public ObjectNode getJsonNode() {
        return mObjectNode;
    }

}
