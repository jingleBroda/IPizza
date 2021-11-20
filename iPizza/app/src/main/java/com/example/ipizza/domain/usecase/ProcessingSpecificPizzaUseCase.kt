package com.example.ipizza.domain.usecase

import android.util.Log
import com.example.ipizza.domain.model.CartModel
import com.example.ipizza.domain.model.PizzaModel
import com.example.ipizza.domain.repositories.DomainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ProcessingSpecificPizzaUseCase(
    private val domainRepos: DomainRepository,
    private val compositeDisposable : CompositeDisposable
) {

    private var onMylistenerGetSpecificDataRoom: ((item: PizzaModel) -> Unit)? = null
    private var onMylistenerGetSpecificOrderPizza:((item: CartModel) -> Unit)? = null

    fun searchSpecificPizza(id:Int){

      val zaprosSpecificGet = domainRepos.searchSpecificPizza(id)
          .subscribeOn(Schedulers.io())//.single())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              {
                  onMylistenerGetSpecificDataRoom?.invoke(it)
              },
              {
                  Log.d("Error", it.localizedMessage)
              }
          )

      compositeDisposable.add(
          zaprosSpecificGet
      )
    }

    fun getSpecificPizza(specificPizza: (item: PizzaModel) -> Unit){
        onMylistenerGetSpecificDataRoom = specificPizza
    }

    fun searchSpecificOrderPizza(name:String){
        val zaprosSpecificOrderPizza = domainRepos.searchSpecificOrderPizza(name)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onMylistenerGetSpecificOrderPizza?.invoke(it)
                },
                {
                    Log.d("Error", it.localizedMessage)
                }
            )

        compositeDisposable.add(
            zaprosSpecificOrderPizza
        )
    }

    fun getSpecificOrderPizza(order: (item: CartModel) -> Unit){
        onMylistenerGetSpecificOrderPizza = order
    }

    fun insertOrderDataRoom(order:CartModel){
        val zaprosInsert = domainRepos.insertOrderPizza(order)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(
            zaprosInsert
        )
    }


}