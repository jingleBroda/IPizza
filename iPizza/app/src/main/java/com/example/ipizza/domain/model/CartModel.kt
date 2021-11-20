package com.example.ipizza.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartTable")
data class CartModel (
    @PrimaryKey
    var name:String ="",
    var imgUrl:String = "",
    var price:Int =0,
    var quantity:Int = 1
)