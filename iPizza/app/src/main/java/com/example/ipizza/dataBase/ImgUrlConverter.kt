package com.example.ipizza.dataBase

import androidx.room.TypeConverter

class ImgUrlConverter {
    @TypeConverter
    fun gettingListFromString(data: String): ArrayList<String> {
        val list = ArrayList<String>()

        var tmpString = ""
        for(i in data.indices){
            if(data[i] ==' ')
            {
                list.add(tmpString)
                tmpString=""
            }
            else{
                tmpString +=data[i]
            }

        }

        return list
    }

    @TypeConverter
    fun writingStringFromList(imageUrls: ArrayList<String>): String {
        var genreIds=""
        for (i in 0 until imageUrls.size) genreIds = genreIds+imageUrls[i]+' '
        return genreIds
    }
}