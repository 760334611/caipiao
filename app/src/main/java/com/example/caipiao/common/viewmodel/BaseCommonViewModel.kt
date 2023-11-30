package com.example.caipiao.common.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caipiao.MyApplication
import com.example.caipiao.common.DefCommonUtils
import com.example.caipiao.common.bean.BaseHistoryPrizeNumber
import com.example.caipiao.common.bean.BaseHistoryRecord
import com.example.caipiao.common.bean.SelectNumber
import com.example.caipiao.daletou.bean.DaHistoryPrizeNumber
import com.example.caipiao.daletou.bean.DaHistoryRecord
import com.example.caipiao.daletou.db.DaHistoryDataBase
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

open class BaseCommonViewModel : ViewModel() {


    var mHistoryTimeList: MutableSharedFlow<ArrayList<BaseHistoryRecord>> =
        MutableSharedFlow()
    var mHistoryPrizeList: MutableSharedFlow<ArrayList<BaseHistoryPrizeNumber>> =
        MutableSharedFlow()

    /**
     * 自选历史号码
     */
    var mHistorySelectList = ArrayList<BaseHistoryRecord>()

    /**
     * 历史开奖记录
     */
    var mHistoryPrizeNumberList = ArrayList<BaseHistoryPrizeNumber>()

    @SuppressLint("SimpleDateFormat")
    var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-mm-dd")

    /**
     * 自选历史号码
     */
    open fun getHistorySelectList() = mHistorySelectList

    /**
     * 历史开奖号码
     */
    open fun getHistoryPrizeNumberList() = mHistoryPrizeNumberList

    open fun insertHistoryData(selectNumberList: ArrayList<SelectNumber>){

    }
    open fun getPrizeDataDB() {

    }

    open fun getHistoryData() {

    }

    open fun getHistoryPrizeData() {

    }

}