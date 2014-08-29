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

import com.google.gson.JsonObject;

/**
 * This abstract class defines a model built from GSON.
 */
public abstract class GsonModel {

    protected final JsonObject mJsonObject;

    public GsonModel(final JsonObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public JsonObject getJsonObject() {
        return mJsonObject;
    }

}
