package com.example.ipizza.fragment.FragmentMainMenu

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.ipizza.bottomFragment.BottomFragment
import com.example.ipizza.recyclerView.AdapterHomeMenuRecyclerView
import com.example.ipizza.retrofit.PizzaModel
import com.example.ipizza.retrofit.RetrofitInstance
import com.example.ipizza.retrofit.RetrofitServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class FragmentMainMenuViewModel:ViewModel() {

    private val retroInstance = RetrofitInstance.getRetroPizzaInstance().create(RetrofitServices::class.java)
    private val compositeDisposable = CompositeDisposable()
    private var onMylistener: ((item: List<PizzaModel>) -> Unit)? = null

    //блок для функций Retrofit
    fun makeApiCallPizza(){

        val localDisposable =  retroInstance.getPizzaFullInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({pizza->

                onMylistener?.invoke(pizza)


            },{
                Log.d("Error", it.localizedMessage)
            })

        compositeDisposable.add(
            localDisposable
        )

    }

    fun getPizza(listPizza: (item: List<PizzaModel>) -> Unit){
        onMylistener = listPizza
    }

    fun clearComposite(){
        compositeDisposable.clear()
    }

}