package com.example.ipizza

import android.app.Application
import androidx.room.Room
import com.example.ipizza.dagger2.DaggerRetroComponent
import com.example.ipizza.dagger2.RetroComponent
import com.example.ipizza.dagger2.RetroModule
import com.example.ipizza.dagger2.RoomDataModule
import com.example.ipizza.dataBase.PizzaDataBase

class App: Application() {

    private lateinit var retroComponent: RetroComponent

    companion object
    {
        lateinit var instance:App
    }

    override fun onCreate() {
        super.onCreate()

        retroComponent = DaggerRetroComponent.builder()
            .retroModule(RetroModule())
            .roomDataModule(RoomDataModule(this))
            .build()

        instance = this

    }

    fun getRetroComponent(): RetroComponent {
        return retroComponent
    }

}