package com.example.caipiao.shuangseqiu.db

import androidx.room.TypeConverter
import com.example.caipiao.shuangseqiu.bean.SelectNumber
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryConverters {

    @TypeConverter
    fun stringToObject(value: String): List<SelectNumber> {
        val listType = object : TypeToken<List<SelectNumber>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<SelectNumber>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}