package com.example.ipizza.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        var baseUrl = "https://springboot-kotlin-demo.herokuapp.com/"
        //https://i-pizza.herokuapp.com/

        fun getRetroPizzaInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

        }

    }
}