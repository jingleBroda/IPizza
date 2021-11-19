package com.example.ipizza.presentation

import android.app.Application
import com.example.ipizza.presentation.dagger2.DaggerRetroComponent
import com.example.ipizza.presentation.dagger2.RetroComponent
import com.example.ipizza.presentation.dagger2.RetroModule
import com.example.ipizza.presentation.dagger2.RoomDataModule

class App: Application() {

    private lateinit var retroComponent: RetroComponent

    companion object
    {
        lateinit var instance: App
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