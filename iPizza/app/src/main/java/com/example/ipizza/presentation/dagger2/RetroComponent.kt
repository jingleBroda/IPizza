package com.example.ipizza.presentation.dagger2

import com.example.ipizza.data.repositoriesImpl.PizzaRepositoriesImpl
import com.example.ipizza.domain.repositories.DomainRepository
import com.example.ipizza.domain.usecase.GetPizzaToPizzaMenuUseCase
import com.example.ipizza.domain.usecase.ProcessingCartPizzaUseCase
import com.example.ipizza.domain.usecase.ProcessingSpecificPizzaUseCase
import com.example.ipizza.presentation.fragment.FragmentMainMenu.FragmentMainMenuViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [RetroModule::class, RoomDataModule::class, ReposModule::class])
interface RetroComponent {

    fun inject(fragmentMainViewModel: FragmentMainMenuViewModel)



}