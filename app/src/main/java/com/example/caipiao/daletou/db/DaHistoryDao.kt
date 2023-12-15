package com.example.caipiao.daletou.db

import androidx.room.*
import com.example.caipiao.common.bean.BaseHistoryRecord
import com.example.caipiao.daletou.bean.DaHistoryPrizeNumber
import com.example.caipiao.daletou.bean.DaHistoryRecord

@Dao
interface DaHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistoryTimeData(historyRecord: DaHistoryRecord): Long

    @Query("SELECT * FROM daHistoryTimeDate")
    fun queryAllHistoryTime(): List<DaHistoryRecord>

    @Update(entity = DaHistoryRecord::class)
    fun updateHistoryTimeData(historyRecord: DaHistoryRecord)

    @Delete(entity = DaHistoryRecord::class)
    fun deleteHistoryTimeData(historyRecord: DaHistoryRecord)

    @Query("SELECT * FROM daHistoryPrizeDate")
    fun queryAllHistoryPrize(): List<DaHistoryPrizeNumber>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistoryPrizeData(historyPrizeNumberList: ArrayList<DaHistoryPrizeNumber>)
}