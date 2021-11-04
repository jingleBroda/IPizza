package com.example.ipizza.fragment.FragmentMainMenu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.ipizza.App
import com.example.ipizza.retrofit.PizzaModel
import com.example.ipizza.retrofit.RetrofitInstance
import com.example.ipizza.retrofit.RetrofitServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class FragmentMainMenuViewModel(application:Application):AndroidViewModel(application){ //:ViewModel() {

    private val retroInstance = RetrofitInstance.getRetroPizzaInstance().create(RetrofitServices::class.java)
    private val compositeDisposable = CompositeDisposable()


    //Test Room
    //private val context = getApplication<Application>().applicationContext
    private val db = App.getInstanceApp().getDB()
    private val dbDao = db.databaseDao()
    private var onMylistenerInsertRoom: ((item: List<PizzaModel>) -> Unit)? = null //List<PizzaModelRoom>
    private var onMylistenerGetAllDataRoom: ((item: List<PizzaModel>) -> Unit)? = null
    private var onMylistenerGetSpecificDataRoom: ((item: PizzaModel) -> Unit)? = null
    //



    //блок для функций Retrofit
    fun makeApiCallPizza(){

        val localDisposable =  retroInstance.getPizzaFullInfo()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({pizza->
                Log.d("ROOM1", "ZAPROS1")

                onMylistenerInsertRoom?.invoke(pizza)

                },
                {
                        Log.d("Error", it.localizedMessage)
                })


        compositeDisposable.add(
            localDisposable
        )

    }

     fun searchSpecificPizza(id:Int){
        val zaprosDB2 = dbDao.getSpecificPizza(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onMylistenerGetSpecificDataRoom?.invoke(it)
                },
                {

                }
            )

        compositeDisposable.add(
            zaprosDB2
        )
    }

    fun getSpecificPizza(specificPizza: (item: PizzaModel) -> Unit){
        onMylistenerGetSpecificDataRoom = specificPizza
    }

    private fun getAllPizzaRoom(){
        val zaprosDB3 = dbDao.getPizza()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("ROOM3",it.toString() ) // it.size.toString()
                    onMylistenerGetAllDataRoom?.invoke(it)
                },
                {

                }
            )

        compositeDisposable.add(
            zaprosDB3
        )
    }

    fun insertDataRoom() {
        insertPizzaRoom {
            Log.d("ROOM2", "ZAPROS2")
            for(i in it.indices)
            {
                val zaprosDB = dbDao.insertPizza(it[i])
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{
                        Log.d("ROOM_insert", it.toString())
                    }

                compositeDisposable.add(
                    zaprosDB
                )
            }

            getAllPizzaRoom()
        }

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