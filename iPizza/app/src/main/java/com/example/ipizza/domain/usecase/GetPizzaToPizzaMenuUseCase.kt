package com.example.ipizza.domain.usecase

import android.util.Log
import com.example.ipizza.domain.model.PizzaModel
import com.example.ipizza.domain.repositories.DomainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GetPizzaToPizzaMenuUseCase(
    private val domainRepos:DomainRepository,
    private val compositeDisposable :CompositeDisposable
    ) {

    fun makeApi(onMylistenerInsertRoom: ((item: List<PizzaModel>) -> Unit)?){
        val localDisposable = domainRepos.makeApiPizza()
           .subscribeOn(Schedulers.single())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({pizza->
               onMylistenerInsertRoom?.invoke(pizza)
           },
               {
                   Log.d("Error", it.localizedMessage)
               })

        compositeDisposable.add(
            localDisposable
        )
   }

    fun insertPizzaToDB(pizza:PizzaModel){
        val zaprosDB = domainRepos.insertDataRoom(pizza)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(
            zaprosDB
        )

    }

    fun getAllPizzaFromDb(onMylistenerGetAllDataRoom: ((item: List<PizzaModel>) -> Unit)?){

        val zaprosGetAllPiza = domainRepos.getAllPizzaRoom()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onMylistenerGetAllDataRoom?.invoke(it)
                },
                {
                    Log.d("Error", it.localizedMessage)
                }
            )

        compositeDisposable.add(
            zaprosGetAllPiza
        )

    }

}