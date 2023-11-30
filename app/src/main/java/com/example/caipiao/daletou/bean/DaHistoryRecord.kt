package com.example.caipiao.daletou.bean


import androidx.room.Entity
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.caipiao.common.bean.BaseHistoryRecord
import com.example.caipiao.common.bean.SelectNumber

@Entity(tableName = "daHistoryTimeDate")
class DaHistoryRecord : BaseHistoryRecord {

    constructor(id: Long, time: Long, prizeDesignatedTime: Long, selectNumberJson: String) : super(id,time,prizeDesignatedTime,selectNumberJson) {
        this.id = id
        this.time = time
        this.prizeDesignatedTime = prizeDesignatedTime
        this.selectNumberJson = selectNumberJson
        selectNumberList = JSONArray.parseArray(
            selectNumberJson,
            SelectNumber::class.java
        ) as ArrayList<SelectNumber>
    }

    constructor(time: Long, prizeDesignatedTime: Long, selectNumberList: ArrayList<SelectNumber>) : super(time,prizeDesignatedTime,selectNumberList) {
        this.time = time
        this.prizeDesignatedTime = prizeDesignatedTime
        this.selectNumberList.clear()
        this.selectNumberList.addAll(selectNumberList)
        this.selectNumberJson = JSONObject.toJSONString(this.selectNumberList)
    }
}