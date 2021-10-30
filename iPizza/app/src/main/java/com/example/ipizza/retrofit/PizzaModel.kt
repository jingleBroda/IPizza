package com.example.ipizza.retrofit

data class PizzaModel (
    val id:Int? = null,
    val name:String? = null,
    val price:Int? = null,
    val imageUrls:ArrayList<String>? = null,
    val description:String?=null
)