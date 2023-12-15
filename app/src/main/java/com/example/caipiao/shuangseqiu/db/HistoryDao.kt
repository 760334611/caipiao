package com.example.caipiao.shuangseqiu.db

import androidx.room.*
import com.example.caipiao.common.bean.BaseHistoryRecord
import com.example.caipiao.shuangseqiu.bean.HistoryPrizeNumber
import com.example.caipiao.shuangseqiu.bean.HistoryRecord

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistoryTimeData(historyRecord: HistoryRecord): Long

    @Query("SELECT * FROM historyTimeDate")
    fun queryAllHistoryTime(): List<HistoryRecord>

    @Update(entity = HistoryRecord::class)
    fun updateHistoryTimeData(vararg historyRecord: HistoryRecord)

    @Delete(entity = HistoryRecord::class)
    fun deleteHistoryTimeData(historyRecord: HistoryRecord)

    @Query("SELECT * FROM historyPrizeDate")
    fun queryAllHistoryPrize(): List<HistoryPrizeNumber>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistoryPrizeData(historyPrizeNumberList: ArrayList<HistoryPrizeNumber>)
}