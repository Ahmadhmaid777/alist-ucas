package com.app.AlistApp.network;


import com.app.AlistApp.model.Transaction;
import com.app.AlistApp.model.ServiceRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ServiceApiInterface {


    @GET("userId")
    public Call<List<Transaction>> getUserTransaction(@Path("id") long userId);

    @GET("8F3V")
    public Call<List<Transaction>> getUserTransaction();

    @POST("")
    Call<ServiceRequest> addServiceRequest(@Body ServiceRequest serviceRequest);


    @GET()
    Call<List<Transaction>> filterTransaction(@Path("id")long userId, @Query("type")String serviceType,@Query("State")String serviceState,@Query("startDate")String stateDate,@Query("endDate")String endDate);



}
