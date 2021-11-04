package com.example.ipizza.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ipizza.retrofit.PizzaModel
import io.reactivex.rxjava3.core.Completable

import io.reactivex.rxjava3.core.Single


@Dao
interface PizzaDao {
    @Query("SELECT * FROM pizzaTable")
    fun getPizza(): Single<List<PizzaModel>>

    @Query("SELECT * FROM pizzaTable WHERE id = :id")
    fun getSpecificPizza(id:Int): Single<PizzaModel>


    @Query("DELETE FROM pizzaTable")
    fun deleteAllPizza():Completable


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertPizza(pizzaList: PizzaModel) :Completable

}