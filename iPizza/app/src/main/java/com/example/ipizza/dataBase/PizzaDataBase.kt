package com.example.ipizza.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ipizza.retrofit.PizzaModel


@Database(entities = [PizzaModel::class], version = 1, exportSchema = true)
@TypeConverters(ImgUrlConverter::class)
abstract class PizzaDataBase:RoomDatabase() {

    abstract fun databaseDao():PizzaDao

}