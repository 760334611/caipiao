package com.example.caipiao.common.bean


import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import java.io.Serializable

open class BaseHistoryPrizeNumber : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var prizeDateTime: String = ""
    var prizeDesignatedTime: Long = 0
    var selectNumberJson: String = ""

    @Ignore
    var mSelectNumber = SelectNumber()

    constructor(
        id: Long,
        prizeDateTime: String,
        prizeDesignatedTime: Long,
        selectNumberJson: String
    ) {
        this.id = id
        this.prizeDateTime = prizeDateTime
        this.prizeDesignatedTime = prizeDesignatedTime
        this.selectNumberJson = selectNumberJson
        mSelectNumber = JSONArray.parseObject(
            selectNumberJson,
            SelectNumber::class.java
        ) as SelectNumber
    }

    constructor(prizeDateTime: String, prizeDesignatedTime: Long, selectNumber: SelectNumber) {
        this.prizeDateTime = prizeDateTime
        this.prizeDesignatedTime = prizeDesignatedTime
        this.mSelectNumber = selectNumber
        this.selectNumberJson = JSONObject.toJSONString(this.mSelectNumber)
    }
}