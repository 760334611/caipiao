package com.example.caipiao.shuangseqiu.bean


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import java.io.Serializable

@Entity(tableName = "historyPrizeDate")
class HistoryPrizeNumber : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var prizeDateTime: Long = 0
    var prizeDesignatedTime: Long = 0
    var selectNumberJson: String = ""

    @Ignore
    var mSelectNumber = SelectNumber()

    constructor(id: Long, prizeDateTime: Long, prizeDesignatedTime: Long, selectNumberJson: String) {
        this.id = id
        this.prizeDateTime = prizeDateTime
        this.prizeDesignatedTime = prizeDesignatedTime
        this.selectNumberJson = selectNumberJson
        mSelectNumber = JSONArray.parseObject(
            selectNumberJson,
            SelectNumber::class.java
        ) as SelectNumber
    }

    constructor(prizeDateTime: Long, prizeDesignatedTime: Long, selectNumber: SelectNumber) {
        this.prizeDateTime = prizeDateTime
        this.prizeDesignatedTime = prizeDesignatedTime
        this.mSelectNumber = selectNumber
        this.selectNumberJson = JSONObject.toJSONString(this.mSelectNumber)
    }
}