package com.example.ipizza.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ipizza.domain.model.PizzaModel
import com.example.ipizza.domain.model.CartModel


@Database(entities = [PizzaModel::class, CartModel::class], version = 1, exportSchema = true)
@TypeConverters(ImgUrlConverter::class)
abstract class PizzaDataBase:RoomDatabase() {

    abstract fun databaseDao():PizzaDao

}