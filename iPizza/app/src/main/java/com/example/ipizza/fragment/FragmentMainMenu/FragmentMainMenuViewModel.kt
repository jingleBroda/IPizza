package com.example.ipizza.fragment.FragmentMainMenu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.ipizza.App
import com.example.ipizza.dataBase.CartModel
import com.example.ipizza.dataBase.PizzaDataBase
import com.example.ipizza.retrofit.PizzaModel
import com.example.ipizza.retrofit.RetrofitInstance
import com.example.ipizza.retrofit.RetrofitServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class FragmentMainMenuViewModel(application:Application):AndroidViewModel(application){ //:ViewModel() {

    @Inject
    lateinit var retroInstance:RetrofitServices

    private val compositeDisposable = CompositeDisposable()

    init{
        (application as App).getRetroComponent().inject(this)
        //(application as App)
    }


    //Test Room

    //потенциально нужен inject
    private val db = App.getInstanceApp().getDB()
    private val dbDao = db.databaseDao()

    private var onMylistenerInsertRoom: ((item: List<PizzaModel>) -> Unit)? = null
    private var onMylistenerGetAllDataRoom: ((item: List<PizzaModel>) -> Unit)? = null
    private var onMylistenerGetSpecificDataRoom: ((item: PizzaModel) -> Unit)? = null
    private var onMylistenerGetOrderDataRoom: ((item: List<CartModel>) -> Unit)? = null


    //



    //блок для функций Retrofit
    fun makeApiCallPizza(){

        val localDisposable =  retroInstance.getPizzaFullInfo()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({pizza->
                //Log.d("ROOM1", "ZAPROS1")

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
        val zaprosDb = dbDao.updateOrder(order)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("ROOM_update", "Произошел апдейт поля в корзине")
            }

        compositeDisposable.add(
            zaprosDb
        )
    }

    fun deleteOrder(){
        val zaprosDb = dbDao.deleteOrder()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("ROOM_deleteOrder", "DELETE")
            }

        compositeDisposable.add(
            zaprosDb
        )
    }

    fun insertOrderDataRoom(order:CartModel) {
        val zaprosDb = dbDao.insertOrderPizza(order)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("ROOM_insertOrder", "Insert")
            }

        compositeDisposable.add(
            zaprosDb
        )
    }

    fun getOrderDataRoom(){
        val zaprosDb = dbDao.getOrder()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("ROOM_getOrder", it.toString())
                    onMylistenerGetOrderDataRoom?.invoke(it)
                },
                {

                }
            )
        compositeDisposable.add(
            zaprosDb
        )
    }

    fun getAllOrder(allOrder: (item: List<CartModel>) -> Unit){
        onMylistenerGetOrderDataRoom = allOrder
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
                   // Log.d("ROOM3",it.toString() )
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
            //Log.d("ROOM2", "ZAPROS2")
            for(i in it.indices)
            {
                val zaprosDB = dbDao.insertPizza(it[i])
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{
                        //Log.d("ROOM_insert", it.toString())
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