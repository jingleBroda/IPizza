package com.example.ipizza.presentation.fragment.FragmentMainMenu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.ipizza.presentation.App
import com.example.ipizza.domain.model.CartModel
import com.example.ipizza.data.dataBase.PizzaDao
import com.example.ipizza.data.dataBase.PizzaDataBase
import com.example.ipizza.data.repositoriesImpl.PizzaRepositoriesImpl
import com.example.ipizza.domain.model.PizzaModel
import com.example.ipizza.data.retrofit.RetrofitServices
import com.example.ipizza.domain.repositories.DomainRepository
import com.example.ipizza.domain.usecase.GetPizzaToPizzaMenuUseCase
import com.example.ipizza.domain.usecase.ProcessingCartPizza
import com.example.ipizza.domain.usecase.ProcessingSpecificPizzaUseCase
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

    //ТЕСТ НОВОЙ АРХИТЕКТУРЫ
    private var pizzaRepos:DomainRepository

    private var useCaseFragmentMainMenu: GetPizzaToPizzaMenuUseCase

    private var useCaseOperationInSpecificPizza: ProcessingSpecificPizzaUseCase

    private var useCaseCartPizza: ProcessingCartPizza
    //



    init{
        daggerInject(application)

        pizzaRepos = PizzaRepositoriesImpl(retroInstance, dbDao)

        useCaseFragmentMainMenu = GetPizzaToPizzaMenuUseCase(pizzaRepos, compositeDisposable)
        useCaseOperationInSpecificPizza = ProcessingSpecificPizzaUseCase(pizzaRepos, compositeDisposable)
        useCaseCartPizza = ProcessingCartPizza(pizzaRepos, compositeDisposable)

    }

    private var onMylistenerInsertRoom: ((item: List<PizzaModel>) -> Unit)? = null
    private var onMylistenerGetAllDataRoom: ((item: List<PizzaModel>) -> Unit)? = null
    private var onMylistenerGetSpecificOrderPizza: ((item: CartModel) -> Unit)? = null

    private fun daggerInject(application:Application){
        (application as App).getRetroComponent().inject(this)
    }

    fun makeApiCallPizza(){
        useCaseFragmentMainMenu.makeApi(onMylistenerInsertRoom)
    }


    fun updateOrder(order: CartModel){
        useCaseCartPizza.updateOrder(order)
    }



    fun deleteOrder(){
        useCaseCartPizza.deleteOrder()
    }

    fun insertOrderDataRoom(order: CartModel) {
        useCaseOperationInSpecificPizza.insertOrderDataRoom(order)
    }

    fun getOrderDataRoom(){
        useCaseCartPizza.getOrderDataRoom()
    }

    fun getAllOrder(allOrder: (item: List<CartModel>) -> Unit){
        useCaseCartPizza.getAllOrder(allOrder)
    }

     fun searchSpecificPizza(id:Int){
     useCaseOperationInSpecificPizza.searchSpecificPizza(id)
    }

    fun getSpecificPizza(specificPizza: (item: PizzaModel) -> Unit){

        useCaseOperationInSpecificPizza.getSpecificPizza(specificPizza)

    }

    private fun getAllPizzaRoom(){
        useCaseFragmentMainMenu.getAllPizzaFromDb(onMylistenerGetAllDataRoom)
    }

    fun insertDataRoom() {
        insertPizzaRoom {
            for(i in it.indices)
            {
                useCaseFragmentMainMenu.insertPizzaToDB(it[i])
            }
            getAllPizzaRoom()
        }
    }

    fun searchSpecificOrderPizza(name:String){
        useCaseOperationInSpecificPizza.searchSpecificOrderPizza(name)
    }

    fun getSpecificOrderPizza(order: (item: CartModel) -> Unit){
        useCaseOperationInSpecificPizza.getSpecificOrderPizza(order)
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