package com.example.caipiao.shuangseqiu.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "historyNumberDate", foreignKeys = [
        ForeignKey(
            entity = HistoryRecord::class,
            parentColumns = ["id"],
            childColumns = ["historyRecordId"],
            onDelete = CASCADE
        )]
)
class SelectNumber {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "historyRecordId", index = true)
    var historyRecordId: Int = 0
    var blueOne: Int = 0
    var blueTwo: Int = 0
    var blueThree: Int = 0
    var blueFour: Int = 0
    var blueFive: Int = 0
    var blueSix: Int = 0
    var redOne: Int = 0

}