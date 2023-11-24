package com.example.caipiao.shuangseqiu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.caipiao.shuangseqiu.bean.HistoryRecord
import com.example.caipiao.shuangseqiu.bean.SelectNumber

@Dao
interface HistoryDao {

    @Insert
    fun insertHistoryTimeData(historyRecord: HistoryRecord): Long

    @Query("SELECT * FROM historyTimeDate")
    fun queryAllHistoryTime(): List<HistoryRecord>

    @Update(entity = HistoryRecord::class)
    fun updateHistoryTimeData(vararg historyRecord: HistoryRecord)
}