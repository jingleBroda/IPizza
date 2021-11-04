package com.example.ipizza

import android.app.Application
import androidx.room.Room
import com.example.ipizza.dataBase.PizzaDataBase

class App: Application() {



    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(this, PizzaDataBase::class.java, "database") .build()
    }

    fun getDB(): PizzaDataBase {
        return db
    }

    companion object
    {
        lateinit var db:PizzaDataBase
        lateinit var instance:App

        fun getInstanceApp(): App {
            return instance
        }
    }

}