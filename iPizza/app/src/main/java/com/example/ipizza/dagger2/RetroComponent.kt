package com.example.ipizza.dagger2

import com.example.ipizza.dataBase.PizzaDataBase
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenuViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [RetroModule::class, RoomDataModule::class])
interface RetroComponent {

    fun inject(fragmentMainViewModel: FragmentMainMenuViewModel)

}