package com.example.ipizza.presentation.dagger2

import com.example.ipizza.data.retrofit.RetrofitServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetroModule {

    val baseURL = "https://springboot-kotlin-demo.herokuapp.com/"

    @Singleton
    @Provides
    fun getRetroServiceInterface(retrofit: Retrofit):RetrofitServices {
        return retrofit.create(RetrofitServices::class.java)
    }

    @Singleton
    @Provides
    fun getRetroFitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

}