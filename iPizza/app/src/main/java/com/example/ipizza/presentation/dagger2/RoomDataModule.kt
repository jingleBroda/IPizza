package com.example.ipizza.presentation.dagger2

import android.app.Application
import androidx.room.Room
import com.example.ipizza.data.dataBase.PizzaDao
import com.example.ipizza.data.dataBase.PizzaDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDataModule(application: Application) {

    private  var dbApplication = application
    private lateinit var db:PizzaDataBase

    @Singleton
    @Provides
    fun providesRoomDatabase(): PizzaDataBase {
        db = Room.databaseBuilder(dbApplication, PizzaDataBase::class.java, "database") .build()
        return db
    }

    @Singleton
    @Provides
    fun providesCategoryDAO(db:PizzaDataBase):PizzaDao{
        return db.databaseDao()
    }

}