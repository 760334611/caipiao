package com.example.caipiao.daletou.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.caipiao.daletou.bean.DaHistoryPrizeNumber
import com.example.caipiao.daletou.bean.DaHistoryRecord

@Database(version = 1, exportSchema = false, entities = [DaHistoryRecord::class, DaHistoryPrizeNumber::class])
abstract class DaHistoryDataBase : RoomDatabase() {

    val mHistoryDao: DaHistoryDao by lazy { getHistoryDao() }
    abstract fun getHistoryDao() : DaHistoryDao

    companion object{
        private var instance: DaHistoryDataBase? = null
        fun getInstance(context: Context): DaHistoryDataBase {
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DaHistoryDataBase::class.java,
                    "DaHistoryData.db").allowMainThreadQueries().build()

            }
            return instance as DaHistoryDataBase
        }
    }
}