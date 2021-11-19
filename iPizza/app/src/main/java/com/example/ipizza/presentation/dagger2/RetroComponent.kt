package com.example.ipizza.presentation.dagger2

import com.example.ipizza.presentation.fragment.FragmentMainMenu.FragmentMainMenuViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [RetroModule::class, RoomDataModule::class])
interface RetroComponent {

    fun inject(fragmentMainViewModel: FragmentMainMenuViewModel)

}