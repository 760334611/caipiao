package com.example.caipiao.shuangseqiu.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.caipiao.shuangseqiu.db.HistoryConverters

@Entity(tableName = "historyTimeDate")
@TypeConverters(HistoryConverters::class)
class HistoryRecord {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var time: Long = 0
    var historyList:List<SelectNumber>

    constructor(id: Int, time: Long, historyList: List<SelectNumber>) {
        this.id = id
        this.time = time
        this.historyList = historyList
    }

}