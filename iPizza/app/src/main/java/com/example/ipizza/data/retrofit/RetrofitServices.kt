package com.example.ipizza.data.retrofit




import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RetrofitServices {
    @GET("/pizza")
    fun getPizzaFullInfo(): Single<List<PizzaModel>>
}