package com.example.ipizza.fragment.FragmentMainMenu

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ipizza.dataBase.PizzaDatabase
import com.example.ipizza.dataBase.PizzaEntity
import com.example.ipizza.observer.MyObserver
import com.example.ipizza.retrofit.PizzaModel
import com.example.ipizza.retrofit.RetrofitInstance
import com.example.ipizza.retrofit.RetrofitServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class FragmentMainMenuViewModel:ViewModel() {

    private lateinit var retroInstance:RetrofitServices

    //блок для функций Retrofit
    fun makeApiCallPizza():RetrofitServices{

       retroInstance = RetrofitInstance.getRetroPizzaInstance().create(RetrofitServices::class.java)

        return retroInstance
    }

}