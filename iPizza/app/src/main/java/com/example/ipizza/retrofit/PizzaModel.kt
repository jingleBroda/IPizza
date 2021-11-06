package com.example.ipizza.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ipizza.dataBase.ImgUrlConverter

@Entity(tableName = "pizzaTable")
data class PizzaModel (
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    var name:String = "",
    var price:Int = 0,
    @TypeConverters(ImgUrlConverter::class)
    var imageUrls:ArrayList<String>? = null,
    var description:String=""
)