package com.example.caipiao.shuangseqiu.bean

import java.io.Serializable


class SelectNumber(var blueList: ArrayList<Int>, var redList: ArrayList<Int>) : Serializable {

    constructor() : this(ArrayList(), ArrayList())

    fun setListData(blueList: ArrayList<Int>, redList: ArrayList<Int>) {
        this.blueList = blueList
        this.redList = redList
    }
}
