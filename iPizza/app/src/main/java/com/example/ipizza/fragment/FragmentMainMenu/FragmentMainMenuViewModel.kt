package com.example.ipizza.fragment.FragmentMainMenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ipizza.dataBase.PizzaDatabase
import com.example.ipizza.dataBase.PizzaEntity

class FragmentMainMenuViewModel:ViewModel() {

    val liveData = MutableLiveData<List<PizzaEntity>>()
    private val itemLiveData = MutableLiveData<PizzaEntity>()
    private val db = PizzaDatabase

    init {
        allLiveData()
    }

    fun allLiveData(){
        liveData.value = db.pizzaDao.getAll()
    }

    fun concretItemLiveData(id:Int):PizzaEntity{
        itemLiveData.value = db.pizzaDao.getById(id)
        return itemLiveData.value!!
    }

}