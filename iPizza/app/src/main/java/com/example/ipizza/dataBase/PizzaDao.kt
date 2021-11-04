package com.example.ipizza.dataBase

import androidx.room.*
import com.example.ipizza.retrofit.PizzaModel
import io.reactivex.rxjava3.core.Completable

import io.reactivex.rxjava3.core.Single


@Dao
interface PizzaDao {
    //запросы для pizzaTable
    @Query("SELECT * FROM pizzaTable")
    fun getPizza(): Single<List<PizzaModel>>

    @Query("SELECT * FROM pizzaTable WHERE id = :id")
    fun getSpecificPizza(id:Int): Single<PizzaModel>

    @Query("DELETE FROM pizzaTable")
    fun deleteAllPizza():Completable

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertPizza(pizzaList: PizzaModel) :Completable
    //

    //запросы для cartTable
    @Query("SELECT * FROM cartTable")
    fun getOrder(): Single<List<CartModel>>

    @Query("DELETE FROM cartTable")
    fun deleteOrder():Completable

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertOrderPizza(orderList: CartModel) :Completable

    @Update
    fun updateOrder(orderList: CartModel) :Completable
    //
}