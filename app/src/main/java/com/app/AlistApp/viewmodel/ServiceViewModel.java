package com.app.AlistApp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.app.AlistApp.model.Transaction;
import com.app.AlistApp.model.ServiceRequest;
import com.app.AlistApp.model.TransactionFilters;
import com.app.AlistApp.model.User;
import com.app.AlistApp.repository.ServiceRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ServiceViewModel extends ViewModel {
    private static final String TAG = "elc_error";
    ServiceRepository repository;

    public User getCurrentUser() {
       return repository.getCurrentUser();
    }


    @Inject
    public ServiceViewModel(ServiceRepository serviceRepository) {
        this.repository = serviceRepository;
    }


    public LiveData<List<Transaction>> getUserTransactions(long userId){
        return repository.getUserTransaction(userId);
    }


    public LiveData<ServiceRequest> addServiceRequest(ServiceRequest serviceRequest) {
        return repository.addServiceRequest(serviceRequest);
    }





    public LiveData<List<Transaction>> filterTransactions(long userId, TransactionFilters filters){
      return repository.filterTransaction(userId,filters);

    }



}
