package com.example.ipizza.domain.usecase

import android.app.Application
import android.util.Log
import com.example.ipizza.domain.model.CartModel
import com.example.ipizza.domain.model.ServerCartModel
import com.example.ipizza.domain.repositories.DomainRepository
import com.example.ipizza.presentation.App
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProcessingCartPizzaUseCase constructor(
    private val domainRepos: DomainRepository,
    private val compositeDisposable : CompositeDisposable
) {
    private var onMylistenerGetOrderDataRoom: ((item: List<CartModel>) -> Unit)? = null

    fun updateOrder(order: CartModel){
        val zaprosUpdate = domainRepos.updateOrder(order)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(
            zaprosUpdate
        )
    }

    fun deleteOrder(){
        val zaprosDelete = domainRepos.deleteOrder()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(
            zaprosDelete
        )
    }

    fun getOrderDataRoom(){

        val zaprosAllOrder = domainRepos.getOrderDataRoom()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onMylistenerGetOrderDataRoom?.invoke(it)
                },
                {
                    Log.d("Error", it.localizedMessage)
                }
            )
        compositeDisposable.add(
            zaprosAllOrder
        )

    }

    fun getAllOrder(allOrder: (item: List<CartModel>) -> Unit){
        onMylistenerGetOrderDataRoom = allOrder
    }

    fun postInServerListPizza(orderList:List<ServerCartModel>){
        val zaprosPostInServer = domainRepos.postPizzaInServer(orderList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(
            zaprosPostInServer
        )
    }

}