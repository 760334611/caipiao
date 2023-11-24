package com.example.caipiao.shuangseqiu.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caipiao.MyApplication
import com.example.caipiao.shuangseqiu.bean.HistoryRecord
import com.example.caipiao.shuangseqiu.bean.SelectNumber
import com.example.caipiao.shuangseqiu.db.HistoryDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainShuangSeQiuViewModel : ViewModel() {
    var mHistoryTimeList: MutableSharedFlow<ArrayList<HistoryRecord>> =
        MutableSharedFlow()

    private var mHistorySelectList = ArrayList<HistoryRecord>()

    private var mHistoryRecord: HistoryRecord = HistoryRecord(0, ArrayList())

    fun getHistoryData() {
        viewModelScope.launch {
            val timeList = withContext(Dispatchers.IO) {
                HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.queryAllHistoryTime()
            }
            mHistorySelectList.clear()
            mHistorySelectList.addAll(timeList)
            mHistoryTimeList.emit(mHistorySelectList)
        }
    }

    fun insertHistoryData() {
        viewModelScope.launch {
            val historyRecord = HistoryRecord(System.currentTimeMillis(), ArrayList())
            var id = withContext(Dispatchers.IO) {
                HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.insertHistoryTimeData(
                    historyRecord
                )
            }
            Log.i("zhanghao", "insertHistoryData id=$id")
            if (id >= 0) {
                historyRecord.id = id
                mHistorySelectList.add(0, historyRecord)
                mHistoryTimeList.emit(mHistorySelectList)
            }
        }

    }

    fun setClickPositionData(historyRecord: HistoryRecord) {
        mHistoryRecord.id = historyRecord.id
        mHistoryRecord.selectNumberJson = historyRecord.selectNumberJson
        mHistoryRecord.time = historyRecord.time
        mHistoryRecord.selectNumberList.clear()
        mHistoryRecord.selectNumberList.addAll(historyRecord.selectNumberList)
    }

    fun uploadClickPosition(selectNumberList: ArrayList<SelectNumber>) {
        viewModelScope.launch {
            mHistoryRecord.setListData(
                System.currentTimeMillis(),
                selectNumberList
            )
            withContext(Dispatchers.IO) {
                HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.updateHistoryTimeData(
                    mHistoryRecord
                )
            }
            for (index in 0 until mHistorySelectList.size) {
                if (mHistorySelectList[index].id == mHistoryRecord.id) {
                    mHistorySelectList[index].selectNumberJson = mHistoryRecord.selectNumberJson
                    mHistorySelectList[index].time = mHistoryRecord.time
                    mHistorySelectList[index].selectNumberList.clear()
                    mHistorySelectList[index].selectNumberList.addAll(mHistoryRecord.selectNumberList)
                    break
                }
            }
            mHistoryTimeList.emit(mHistorySelectList)
        }
    }
}