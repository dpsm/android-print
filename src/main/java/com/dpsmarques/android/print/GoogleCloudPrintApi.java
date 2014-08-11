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

/**
 * Created by david.marques on 2014-08-06.
 */

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import rx.Observable;

/**
 * This interface defines the supported Google Cloud Print API features.
 */
public interface GoogleCloudPrintApi {

    /**
     * Retrieves the Google cloud printers associated with the owner of the
     * specified access token.
     *
     * @param token an OAuth token used to access the user printer.
     *
     * @return an Observable which emits a Response corresponding to the search
     * result.
     */
    @GET("/search")
    Observable<Response> getPrinters(@Header("Authorization") String token);

    /**
     * Submits a print job to the specified printer.
     *
     * @param token an OAuth token used to access the user printer.
     * @param printerID the ID of the cloud printer.
     * @param title the title of the print job.
     * @param ticket a print ticket in CJT format.
     * @param content the content of the file to be printed.
     */
    @Multipart
    @POST("/submit")
    Observable<Response> submitPrintJob(@Header("Authorization") String token,
                                        @Part("printerid") TypedString printerID,
                                        @Part("title") TypedString title,
                                        @Part("ticket") TypedString ticket,
                                        @Part("content") TypedFile content);
}
