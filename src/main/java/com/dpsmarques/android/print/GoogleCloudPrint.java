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
package com.dpsmarques.android.print;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.Header;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import rx.Observable;

/**
 * Implements GoogleCloudPrintApi accessing the Google Cloud Print API endpoints over HTTP and
 * abstracts the specifics of each HTTP requests for the respective API.
 */
public class GoogleCloudPrint implements GoogleCloudPrintApi {

    private static final String BEARER_TOKEN_FORMAT = "Bearer %s";

    private final GoogleCloudPrintApi mGoogleCloudPrintApi;

    public GoogleCloudPrint() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("https://www.google.com/cloudprint")
            .setLogLevel(RestAdapter.LogLevel.NONE)
            .build();

        mGoogleCloudPrintApi = restAdapter.create(GoogleCloudPrintApi.class);
    }

    @Override
    public Observable<Response> getPrinters(@Header("Authorization") final String token) {
        return mGoogleCloudPrintApi.getPrinters(formatToken(token));
    }

    @Override
    public Observable<Response> submitPrintJob(@Header("Authorization") final String token,
                                               @Part("printerid") final TypedString printerID,
                                               @Part("title") final TypedString title,
                                               @Part("ticket") final TypedString ticket,
                                               @Part("content") final TypedFile content) {
        return mGoogleCloudPrintApi.submitPrintJob(formatToken(token),
                printerID,
                title,
                ticket,
                content);
    }

    private static String formatToken(final String token) {
        return String.format(BEARER_TOKEN_FORMAT, token);
    }
}
