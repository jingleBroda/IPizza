package com.example.ipizza.presentation

import android.app.Application
import com.example.ipizza.presentation.dagger2.*

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
            .reposModule(ReposModule())
            .build()

        instance = this

    }

    fun getRetroComponent(): RetroComponent {
        return retroComponent
    }

}