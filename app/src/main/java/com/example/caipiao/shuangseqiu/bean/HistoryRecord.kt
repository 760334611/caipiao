package com.example.caipiao.shuangseqiu.bean


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import java.io.Serializable

@Entity(tableName = "historyTimeDate")
class HistoryRecord : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var time: Long = 0
    var prizeDesignatedTime: Long = 0
    var selectNumberJson: String = ""


    @Ignore
    var selectNumberList = ArrayList<SelectNumber>()

    constructor(id: Long, time: Long, prizeDesignatedTime: Long, selectNumberJson: String) {
        this.id = id
        this.time = time
        this.prizeDesignatedTime = prizeDesignatedTime
        this.selectNumberJson = selectNumberJson
        selectNumberList = JSONArray.parseArray(
            selectNumberJson,
            SelectNumber::class.java
        ) as ArrayList<SelectNumber>
    }

    constructor(time: Long, prizeDesignatedTime: Long, selectNumberList: ArrayList<SelectNumber>) {
        this.time = time
        this.prizeDesignatedTime = prizeDesignatedTime
        this.selectNumberList.clear()
        this.selectNumberList.addAll(selectNumberList)
        this.selectNumberJson = JSONObject.toJSONString(this.selectNumberList)
    }
}