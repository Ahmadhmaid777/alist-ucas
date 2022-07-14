package com.app.AlistApp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.AlistApp.model.Transaction;
import com.app.AlistApp.model.ServiceRequest;
import com.app.AlistApp.model.TransactionFilters;
import com.app.AlistApp.network.ServiceApiInterface;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import com.app.AlistApp.model.User;
import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ServiceRepository {
    public static String base_url="https://jsonkeeper.com/b/" ;
    private static final String TAG = "service_api";
    MutableLiveData<List<Transaction>>transactions;
    private ServiceApiInterface serviceApi;
    @Inject
    public ServiceRepository(ServiceApiInterface serviceApi) {
        this.serviceApi=serviceApi;
    }

    public LiveData<List<Transaction>> getUserTransaction(long userId){
        MutableLiveData<List<Transaction>> transactions=new MutableLiveData<>();
        serviceApi.getUserTransaction().enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful()){
                    transactions.setValue(response.body());
                }else {
                    transactions.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Log.e(TAG, t.getMessage() );

            }
        });
       return transactions;
    }


    public User getCurrentUser(){
        return new User(1,"ahmed",1,"05999999","gaza");
    }





    public LiveData<ServiceRequest> addServiceRequest(ServiceRequest serviceRequest){
        MutableLiveData<ServiceRequest> data=new MutableLiveData<>();
//        serviceApi.addServiceRequest(serviceRequest).enqueue(new Callback<ServiceRequest>() {
//            @Override
//            public void onResponse(Call<ServiceRequest> call, Response<ServiceRequest> response) {
//
//
//                if (response.isSuccessful()) {
//                    data.setValue(response.body());
//                } else {
//                    data.setValue(null);
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ServiceRequest> call, Throwable t) {
//                Log.e(TAG, t.getMessage());
//                data.setValue(null);
//
//            }
//        });


       data.setValue(serviceRequest);


       return data;
    }

    public LiveData<List<Transaction>> filterTransaction(long userId, TransactionFilters filters){
        transactions=new MutableLiveData<>();
//        serviceApi.filterTransaction(userId,filters.getServiceType(),filters.getServiceType(),filters.getStartDate().toString(),filters.getEndDate().toString())
//                .enqueue(new Callback<List<Transaction>>() {
//            @Override
//            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
//                if (response.isSuccessful()){
//                    transactions.setValue(response.body());
//                }else {
//                    transactions=null;
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Transaction>> call, Throwable t) {
//                Log.d(TAG, t.getMessage());
//                transactions.setValue(null);
//
//            }
//        });

       return transactions;
    }








}
