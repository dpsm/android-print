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
package com.github.dpsm.android.print;

/**
 * Created by david.marques on 2014-08-06.
 */

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
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
     *
     * @return an Observable which emits a Response corresponding to the search
     * result.
     */
    @Multipart
    @POST("/submit")
    Observable<Response> submitPrintJob(@Header("Authorization") String token,
                                        @Part("printerid") String printerID,
                                        @Part("title") String title,
                                        @Part("ticket") String ticket,
                                        @Part("content") TypedFile content,
                                        @Part("contentType") String contentType);

    /**
     * Retrieves a printer's information.
     *
     * @param token an OAuth token used to access the user printer.
     * @param printerID the ID of the cloud printer.
     * @param useCDDFormat Include this parameter with a value of "true" to retrieve the printer's
     *                     capabilities in <a href="https://developers.google.com/cloud-print/docs/cdd#cdd">CDD</a>
     *                     format (the capabilities stored for the printer are translated to CDD
     *                     format if they were provided in a native format).
     * @param extraFields Comma-separated list of extra fields to include in the returned printer
     *                    object, see <a href="https://developers.google.com/cloud-print/docs/appInterfaces#extra_fields">Extra Fields</a>
     *                    for Printers for more information.
     * @return an Observable which emits a Response corresponding to the search
     * result.
     */
    @GET("/printer")
    Observable<Response> getPrinter(@Header("Authorization") String token,
                                    @Query("printerid") String printerID,
                                    @Query("use_cdd") boolean useCDDFormat,
                                    @Query("extra_fields") String extraFields);

    /**
     * Retrieves a printer's jobs information.
     *
     * @param token an OAuth token used to access the user printer.
     * @param printerID the ID of the cloud printer.
     * @param jobOwner filters the jobs by only returning jobs submitted by the specified user.
     * @param jobStatus filters the jobs by only returning jobs with a given <a href="https://developers.google.com/cloud-print/docs/cdd#oldjobstatus">status</a>.
     *
     * @return an Observable which emits a Response corresponding to the search
     * result.
     */
    @GET("/jobs")
    Observable<Response> getJobs(@Header("Authorization") String token,
                                 @Query("printerid") String printerID,
                                 @Query("owner") String jobOwner,
                                 @Query("status") String jobStatus);


    /**
     * Deletes a previously submitted job.
     *
     * @param token an OAuth token used to access the user printer.
     * @param jobID the job unique identifier.
     *
     * @see #getJobs(String, String, String, String)
     *
     * @return an Observable which emits a Response corresponding to the search
     * result.
     */
    @GET("/deletejob")
    Observable<Response> deleteJob(@Header("Authorization") String token,
                                   @Query("jobid") String jobID);

}
