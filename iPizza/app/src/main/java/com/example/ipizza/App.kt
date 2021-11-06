package com.example.ipizza

import android.app.Application
import androidx.room.Room
import com.example.ipizza.dagger2.DaggerRetroComponent
import com.example.ipizza.dagger2.RetroComponent
import com.example.ipizza.dagger2.RetroModule
import com.example.ipizza.dataBase.PizzaDataBase

class App: Application() {

    private lateinit var retroComponent: RetroComponent

    override fun onCreate() {
        super.onCreate()

        retroComponent = DaggerRetroComponent.builder()
            .retroModule(RetroModule())
            .build()

        instance = this
        db = Room.databaseBuilder(this, PizzaDataBase::class.java, "database") .build()
    }

    fun getDB(): PizzaDataBase {
        return db
    }

    fun getRetroComponent(): RetroComponent {
        return retroComponent
    }

    companion object
    {
        //потенциально нужен inject
        lateinit var db:PizzaDataBase

        lateinit var instance:App

        fun getInstanceApp(): App {
            return instance
        }
    }

}