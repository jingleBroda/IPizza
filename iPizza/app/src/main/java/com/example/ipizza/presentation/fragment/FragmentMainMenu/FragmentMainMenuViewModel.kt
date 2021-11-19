package com.example.ipizza.presentation.fragment.FragmentMainMenu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.ipizza.presentation.App
import com.example.ipizza.data.dataBase.CartModel
import com.example.ipizza.data.dataBase.PizzaDao
import com.example.ipizza.data.dataBase.PizzaDataBase
import com.example.ipizza.data.retrofit.PizzaModel
import com.example.ipizza.data.retrofit.RetrofitServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class FragmentMainMenuViewModel(application:Application):AndroidViewModel(application){ //:ViewModel() {

    @Inject
    lateinit var retroInstance:RetrofitServices

    @Inject
    lateinit var db:PizzaDataBase

    @Inject
    lateinit var dbDao:PizzaDao

    private val compositeDisposable = CompositeDisposable()

    init{
        daggerInject(application)
    }

    private var onMylistenerInsertRoom: ((item: List<PizzaModel>) -> Unit)? = null
    private var onMylistenerGetAllDataRoom: ((item: List<PizzaModel>) -> Unit)? = null
    private var onMylistenerGetSpecificDataRoom: ((item: PizzaModel) -> Unit)? = null
    private var onMylistenerGetOrderDataRoom: ((item: List<CartModel>) -> Unit)? = null
    private var onMylistenerGetSpecificOrderPizza: ((item: CartModel) -> Unit)? = null

    private fun daggerInject(application:Application){
        (application as App).getRetroComponent().inject(this)
    }

    fun makeApiCallPizza(){

        val localDisposable =  retroInstance.getPizzaFullInfo()
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


    fun updateOrder(order: CartModel){
        val zaprosUpdate = dbDao.updateOrder(order)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(
            zaprosUpdate
        )
    }



    fun deleteOrder(){
        val zaprosDelete = dbDao.deleteOrder()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(
            zaprosDelete
        )
    }

    fun insertOrderDataRoom(order:CartModel) {
        val zaprosInsert = dbDao.insertOrderPizza(order)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(
            zaprosInsert
        )
    }

    fun getOrderDataRoom(){
        val zaprosAllOrder = dbDao.getOrder()
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

     fun searchSpecificPizza(id:Int){
        val zaprosSpecificGet = dbDao.getSpecificPizza(id)
            .subscribeOn(Schedulers.single())
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

    private fun getAllPizzaRoom(){
        val zaprosGetAllPiza = dbDao.getPizza()
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

    fun insertDataRoom() {
        insertPizzaRoom {
            for(i in it.indices)
            {
                val zaprosDB = dbDao.insertPizza(it[i])
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()

                compositeDisposable.add(
                    zaprosDB
                )
            }

            getAllPizzaRoom()
        }

    }

    fun searchSpecificOrderPizza(name:String){
        val zaprosSpecificOrderPizza = dbDao.getSpecificOrferPizza(name)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onMylistenerGetSpecificOrderPizza?.invoke(it)
                    //Log.d("qqqqq", "on est")
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

    fun getAllPizza(listPizza: (item: List<PizzaModel>) -> Unit){
        onMylistenerGetAllDataRoom = listPizza
    }

    private fun insertPizzaRoom(listPizza: (item: List<PizzaModel>) -> Unit){
        onMylistenerInsertRoom = listPizza
    }

    fun clearComposite(){
        compositeDisposable.clear()
    }



}