package com.example.ipizza.retrofit

data class PizzaModel (
    val id:Int = 0,
    val name:String = "",
    val price:Int = 0,
    val imageUrls:ArrayList<String>? = null,
    val description:String=""
)