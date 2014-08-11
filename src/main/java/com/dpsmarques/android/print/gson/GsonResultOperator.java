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

import com.dpsmarques.android.print.gson.model.GsonModel;
import com.dpsmarques.android.print.gson.model.GsonPrinter;
import com.dpsmarques.android.print.gson.model.GsonPrinterSearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * This Observable.Operator implementation will convert a retrofit.client.Response body into a
 * type T that can be de-serialized through GSON.
 */
public class GsonResultOperator<T extends GsonModel> implements Observable.Operator<T, Response> {

    private static final class GsonAdapter <T extends GsonModel> implements JsonDeserializer<T> {

        @Override
        public T deserialize(final JsonElement json, final Type typeOfT,
                             final JsonDeserializationContext context) throws JsonParseException {
            final T result = GSON.fromJson(json, typeOfT);
            result.setJsonObject(json.getAsJsonObject());
            return result;
        }
    }

    private static final Gson GSON_PROXY;

    private static final Gson GSON;

    static {
        GSON_PROXY = new GsonBuilder()
                .registerTypeAdapter(GsonPrinterSearchResult.class, new GsonAdapter<GsonPrinterSearchResult>())
                .registerTypeAdapter(GsonPrinter.class, new GsonAdapter<GsonPrinter>())
                .create();
        GSON = new GsonBuilder().create();
    }


    private final Class<T> mClass;

    private final Gson mGSON;

    /**
     * Creates an instance of the operator for the specified target class type that uses the
     * specified Gson instance to de-serialize.
     *
     * @param gson the Gson instance to use for de-serialization.
     * @param clazz the target type to convert to.
     */
    public GsonResultOperator(final Gson gson, final Class<T> clazz) {
        mClass = clazz;
        mGSON = gson;
    }

    /**
     * Creates an instance of the operator for the specified target class type.
     *
     * @param clazz the target type to convert to.
     */
    public GsonResultOperator(final Class<T> clazz) {
        this(GSON_PROXY, clazz);
    }

    @Override
    public Subscriber<? super Response> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<Response>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(final Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(final Response response) {
                switch (response.getStatus()) {
                    case HttpURLConnection.HTTP_OK:
                        InputStreamReader reader = null;
                        try {
                            reader = new InputStreamReader(response.getBody().in());
                            final T result = mGSON.fromJson(new JsonReader(reader), mClass);
                            if (result != null) {
                                subscriber.onNext(result);
                            }
                        } catch (IOException e) {
                            subscriber.onError(e);
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                    // Nothing to do here
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }
}
