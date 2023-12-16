package com.example.caipiao.shuangseqiu.bean


import androidx.room.Entity
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.caipiao.common.bean.BaseHistoryPrizeNumber
import com.example.caipiao.common.bean.SelectNumber

@Entity(tableName = "historyPrizeDate")
class HistoryPrizeNumber : BaseHistoryPrizeNumber {

    constructor(id: Long, prizeDateTime: String, prizeDesignatedTime: Long, selectNumberJson: String)
            : super(id,prizeDateTime,prizeDesignatedTime,selectNumberJson) {
        this.id = id
        this.prizeDateTime = prizeDateTime
        this.prizeDesignatedTime = prizeDesignatedTime
        this.selectNumberJson = selectNumberJson
        mSelectNumber = JSONArray.parseObject(
            selectNumberJson,
            SelectNumber::class.java
        ) as SelectNumber
    }

    constructor(prizeDateTime: String, prizeDesignatedTime: Long, selectNumber: SelectNumber)
            : super(prizeDateTime,prizeDesignatedTime,selectNumber){
        this.prizeDateTime = prizeDateTime
        this.prizeDesignatedTime = prizeDesignatedTime
        this.mSelectNumber = selectNumber
        this.selectNumberJson = JSONObject.toJSONString(this.mSelectNumber)
    }
}