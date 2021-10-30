package com.example.ipizza.observer

import android.util.Log
import com.example.ipizza.retrofit.PizzaModel

class MyObserver<T>(private var data: List<PizzaModel>? = null) {
    private var subscribers = hashMapOf<Class<out Any>, (data:List<PizzaModel>)->Unit>()

    fun subscribe(subscriber:Any, onDataChange:(data:List<PizzaModel>)->Unit){
        this.subscribers[subscriber::class.java] = onDataChange
    }

    fun setNewData(data: List<PizzaModel>){
        this.data = data
        subscribers.forEach{map->
            map.value.invoke(data)
        }
    }

    fun removeSubscriber(subscriber:Any){
        subscribers.remove(subscriber::class.java)
    }

    fun obrError(e:Throwable){
        Log.d("kurwa", "qwqwq "+e.toString())
    }

}