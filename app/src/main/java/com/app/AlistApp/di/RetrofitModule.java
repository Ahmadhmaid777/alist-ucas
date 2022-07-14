package com.app.AlistApp.di;

import com.app.AlistApp.network.ServiceApiInterface;
import com.app.AlistApp.repository.ServiceRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {




    @Provides
    @Singleton
    public ServiceApiInterface provideServiceApiInterface(){
        return   new Retrofit
                .Builder()
                .baseUrl(ServiceRepository.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceApiInterface.class);


    }
}
