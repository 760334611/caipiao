package com.example.caipiao.common.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caipiao.MyApplication
import com.example.caipiao.common.bean.BaseHistoryPrizeNumber
import com.example.caipiao.common.bean.BaseHistoryRecord
import com.example.caipiao.common.bean.SelectNumber
import com.example.caipiao.common.bean.WeightSortBean
import com.example.caipiao.shuangseqiu.db.HistoryDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class BaseCommonViewModel : ViewModel() {
    var selectBlueTotalMap = HashMap<Int, WeightSortBean>()
    var selectRedTotalMap = HashMap<Int, WeightSortBean>()

    var blueTotalWeight: Int = 0
    var redTotalWeight: Int = 0
    var baseNumber: Int = 30

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

    open fun insertHistoryData(selectNumberList: ArrayList<SelectNumber>) {

    }

    open fun getPrizeDataDB() {

    }

    open fun getHistoryData() {

    }

    open fun getHistoryPrizeData() {

    }

    /**
     * 删除自选历史号码
     */
    open fun deleteHistorySelectList(mBaseHistoryRecordList: ArrayList<BaseHistoryRecord>) {

    }


    /**
     * 计算历史中奖值的概率
     */
    open fun dealHistoryPrizeNumber(blueNumber: Int, redNumber: Int) {
        selectBlueTotalMap.clear()
        selectRedTotalMap.clear()
        blueTotalWeight = 0
        redTotalWeight = 0
        for (i in 1..blueNumber) {
            selectBlueTotalMap[i] = WeightSortBean(0, 1)
        }
        for (k in 1..redNumber) {
            selectRedTotalMap[k] = WeightSortBean(0, 1)
        }

        val dateNumber = when (mHistoryPrizeNumberList.size > baseNumber) {
            false -> mHistoryPrizeNumberList.size
            true -> baseNumber
        }

        run breaking@{
            for (i in 0 until dateNumber) {
                val blueSelectNumber = mHistoryPrizeNumberList[i].mSelectNumber.blueList
                val redSelectNumber = mHistoryPrizeNumberList[i].mSelectNumber.redList
                for (k in 0 until blueSelectNumber.size) {
                    if (selectBlueTotalMap.containsKey(blueSelectNumber[k])) {
                        selectBlueTotalMap[blueSelectNumber[k]]!!.endWeight =
                            selectBlueTotalMap[blueSelectNumber[k]]!!.endWeight + 1
                    } else {
                        selectBlueTotalMap[blueSelectNumber[k]] = WeightSortBean(0, 1)
                    }
                }
                for (j in 0 until redSelectNumber.size) {
                    if (selectRedTotalMap.containsKey(redSelectNumber[j])) {
                        selectRedTotalMap[redSelectNumber[j]]!!.endWeight =
                            selectRedTotalMap[redSelectNumber[j]]!!.endWeight + 1
                    } else {
                        selectRedTotalMap[redSelectNumber[j]] = WeightSortBean(0, 1)
                    }
                }
            }
        }

        selectBlueTotalMap.forEach { (key, value) ->
            Log.i("zhanghao", "selectBlueTotalMap value.endWeight=${value.endWeight}")
            selectBlueTotalMap[key]!!.startWeight = blueTotalWeight
            blueTotalWeight += (baseNumber/value.endWeight) * 10000
            selectBlueTotalMap[key]!!.endWeight = blueTotalWeight
        }
        selectRedTotalMap.forEach { (key, value) ->
            Log.i("zhanghao", "selectRedTotalMap value.endWeight=${value.endWeight}")
            selectRedTotalMap[key]!!.startWeight = redTotalWeight
            redTotalWeight += (baseNumber/value.endWeight) * 10000
            selectRedTotalMap[key]!!.endWeight = redTotalWeight
        }
    }

}