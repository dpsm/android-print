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

import com.github.dpsm.android.print.jackson.model.JacksonModel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;

import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * This Observable.Operator implementation will convert a retrofit.client.Response body into a
 * type T that can be de-serialized through Jackson.
 */
public class JacksonResultOperator<T extends JacksonModel> implements Observable.Operator<T, Response> {

    private final Class<T> mClass;

    private static final ObjectMapper SMAPPER = new ObjectMapper();

    static {
        SMAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private final ObjectMapper mMapper;

    private final Constructor<T> mConstructor;

    /**
     * Creates an instance of the operator for the specified target class type that uses the
     * specified ObjectMapper instance to de-serialize.
     *
     * @param mapper the ObjectMapper instance to use for de-serialization.
     * @param clazz the target type to convert to.
     */
    public JacksonResultOperator(final ObjectMapper mapper, final Class<T> clazz) {
        try {
            mConstructor = clazz.getConstructor(ObjectNode.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }

        mClass = clazz;
        mMapper = mapper;
    }

    /**
     * Creates an instance of the operator for the specified target class type.
     *
     * @param clazz the target type to convert to.
     */
    public JacksonResultOperator(final Class<T> clazz) {
        this(SMAPPER, clazz);
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
                            final ObjectNode rootNode = mMapper.readValue(reader, ObjectNode.class);
                            T result = mConstructor.newInstance(rootNode);
                            subscriber.onNext(result);
                        } catch (Exception e) {
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
