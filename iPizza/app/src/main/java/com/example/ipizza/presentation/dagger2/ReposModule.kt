package com.example.ipizza.presentation.dagger2

import com.example.ipizza.data.dataBase.PizzaDao
import com.example.ipizza.data.repositoriesImpl.PizzaRepositoriesImpl
import com.example.ipizza.data.retrofit.RetrofitServices
import com.example.ipizza.domain.repositories.DomainRepository
import dagger.Module
import dagger.Provides

@Module
class ReposModule {

    @Provides
    fun getRepos(insideRetroInstance: RetrofitServices,
                 insidedbDao: PizzaDao
    ): DomainRepository {
        return PizzaRepositoriesImpl(insideRetroInstance,insidedbDao)
    }
}