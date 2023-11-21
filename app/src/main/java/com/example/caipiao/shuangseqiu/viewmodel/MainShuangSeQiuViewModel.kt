package com.example.caipiao.shuangseqiu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caipiao.MyApplication
import com.example.caipiao.shuangseqiu.bean.HistoryRecord
import com.example.caipiao.shuangseqiu.db.HistoryDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainShuangSeQiuViewModel:ViewModel() {
    var mHistoryTimeList: MutableSharedFlow<List<HistoryRecord>> =
        MutableSharedFlow()

    fun getHistoryData(){
        viewModelScope.launch {
            val timeList= withContext(Dispatchers.IO){
                HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.queryAllHistoryTime()
            }
            mHistoryTimeList.emit(timeList)
        }
    }
}