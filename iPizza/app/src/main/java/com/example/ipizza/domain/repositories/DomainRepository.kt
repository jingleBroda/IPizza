package com.example.ipizza.domain.repositories

import com.example.ipizza.domain.model.CartModel
import com.example.ipizza.domain.model.PizzaModel
import com.example.ipizza.domain.model.ServerCartModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DomainRepository {

    fun makeApiPizza(): Single<List<PizzaModel>>
    fun insertDataRoom(pizza:PizzaModel): Completable
    fun getAllPizzaRoom():Single<List<PizzaModel>>

    fun searchSpecificPizza(id:Int):Single<PizzaModel>
    fun searchSpecificOrderPizza(name:String):Single<CartModel>
    fun insertOrderPizza(order: CartModel):Completable

    fun updateOrder(order: CartModel):Completable
    fun deleteOrder():Completable
    fun getOrderDataRoom():Single<List<CartModel>>
    fun postPizzaInServer(orderList:List<ServerCartModel>):Completable

}