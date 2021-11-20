package com.example.ipizza.data.retrofit

import com.example.ipizza.domain.model.PizzaModel
import com.example.ipizza.domain.model.ServerCartModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitServices {
    @GET("/pizza")
    fun getPizzaFullInfo(): Single<List<PizzaModel>>

    @Headers("Content-type: application/json")
    @POST("/pizza/order")
    fun postPizzaOrder(@Body orderList:List<ServerCartModel>):Completable
}