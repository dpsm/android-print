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

import android.text.TextUtils;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.Header;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;
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

    GoogleCloudPrint(final GoogleCloudPrintApi googleCloudPrintApi) {
        mGoogleCloudPrintApi = googleCloudPrintApi;
    }

    @Override
    public Observable<Response> getPrinters(@Header("Authorization") final String token) {
        assertNotNullOrEmpty("Token", token);
        return mGoogleCloudPrintApi.getPrinters(formatToken(token));
    }

    @Override
    public Observable<Response> submitPrintJob(@Header("Authorization") final String token,
                                               @Part("printerid") final String printerID,
                                               @Part("title") final String title,
                                               @Part("ticket") final String ticket,
                                               @Part("content") final TypedFile content) {
        assertNotNullOrEmpty("Token", token);
        assertNotNullOrEmpty("Printer ID", printerID);
        assertNotNullOrEmpty("Job Title", title);
        assertNotNullOrEmpty("Job Ticket", ticket);
        assertNotNullOrEmpty("Job Content", content);

        return mGoogleCloudPrintApi.submitPrintJob(formatToken(token),
                printerID,
                title,
                ticket,
                content);
    }

    private void assertNotNullOrEmpty(final String message, final String string) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException(message + " can not be null or empty.");
        }
    }

    @Override
    public Observable<Response> getPrinter(@Header("Authorization") final String token,
                                           @Query("printerid") final String printerID,
                                           @Query("use_cdd") final boolean useCDDFormat,
                                           @Query("extra_fields") final String extraFields) {
        assertNotNullOrEmpty("Token", token);
        assertNotNullOrEmpty("Printer ID", printerID);

        return mGoogleCloudPrintApi.getPrinter(formatToken(token), printerID, useCDDFormat, extraFields);
    }

    @Override
    public Observable<Response> getJobs(@Header("Authorization") final String token,
                                        @Query("printerid") final String printerID,
                                        @Query("owner") final String jobOwner,
                                        @Query("status") final String jobStatus) {
        assertNotNullOrEmpty("Token", token);
        assertNotNullOrEmpty("Printer ID", printerID);

        return mGoogleCloudPrintApi.getJobs(formatToken(token), printerID, jobOwner, jobStatus);
    }

    @Override
    public Observable<Response> deleteJob(@Header("Authorization") final String token,
                                          @Query("jobid") final String jobID) {
        assertNotNullOrEmpty("Token", token);
        assertNotNullOrEmpty("Job ID", jobID);

        return mGoogleCloudPrintApi.deleteJob(formatToken(token), jobID);
    }

    private void assertNotNullOrEmpty(final String message, final TypedInput input) {
        if (input == null || input.length() == 0) {
            throw new IllegalArgumentException(message + " can not be null or empty.");
        }
    }

    private static String formatToken(final String token) {
        return String.format(BEARER_TOKEN_FORMAT, token);
    }
}
