package com.example.ipizza.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartTable")
data class CartModel (
    //@PrimaryKey(autoGenerate = true) val id:Int=0,
    var imgUrl:String = "",
    var price:Int =0,
    var quantity:Int = 1,
    @PrimaryKey //(autoGenerate = true)
    var name:String =""
)