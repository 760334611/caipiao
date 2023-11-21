package com.example.caipiao.shuangseqiu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.caipiao.shuangseqiu.bean.HistoryRecord
import com.example.caipiao.shuangseqiu.bean.SelectNumber

@Dao
interface HistoryDao {

    @Insert
    fun insertHistoryTimeData(historyRecord: HistoryRecord): Long


    @Query("SELECT time,id,historyList FROM historyTimeDate")
    fun queryAllHistoryTime():List<HistoryRecord>
}