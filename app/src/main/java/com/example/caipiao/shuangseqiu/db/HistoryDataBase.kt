package com.example.caipiao.shuangseqiu.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.caipiao.shuangseqiu.bean.HistoryRecord

@Database(version = 1, exportSchema = false, entities = [HistoryRecord::class])
abstract class HistoryDataBase : RoomDatabase() {

    val mHistoryDao: HistoryDao by lazy { getHistoryDao() }
    abstract fun getHistoryDao() : HistoryDao

    companion object{
        private var instance: HistoryDataBase? = null
        fun getInstance(context: Context): HistoryDataBase{
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDataBase::class.java,
                    "HistoryData.db").allowMainThreadQueries().build()

            }
            return instance as HistoryDataBase
        }
    }
}