package com.example.caipiao.shuangseqiu.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.caipiao.MyApplication
import com.example.caipiao.common.DefCommonUtils
import com.example.caipiao.common.bean.BaseHistoryRecord
import com.example.caipiao.shuangseqiu.bean.HistoryPrizeNumber
import com.example.caipiao.shuangseqiu.bean.HistoryRecord
import com.example.caipiao.common.bean.SelectNumber
import com.example.caipiao.common.viewmodel.BaseCommonViewModel
import com.example.caipiao.shuangseqiu.db.HistoryDataBase
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.collections.ArrayList

class MainShuangSeQiuViewModel : BaseCommonViewModel() {


    override fun getHistoryPrizeData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                downloadFile(
                    DefCommonUtils.SHUANG_SE_QIU_URL,
                    DefCommonUtils.SHUANG_SE_QIU_FILE,
                    DefCommonUtils.SHUANG_SE_QIU_NAME
                )
            }
        }
    }

    override fun getPrizeDataDB() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val timeList = withContext(Dispatchers.IO) {
                    HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.queryAllHistoryPrize()
                }
                mHistoryPrizeNumberList.clear()
                mHistoryPrizeNumberList.addAll(timeList)
                mHistoryPrizeNumberList.sortByDescending { historyPrizeNumber -> historyPrizeNumber.prizeDesignatedTime }
                dealHistoryPrizeNumber(DefCommonUtils.SHUANG_SE_QIU_BLUE_NUMBER,DefCommonUtils.SHUANG_SE_QIU_RED_NUMBER)
                mHistoryPrizeList.emit(mHistoryPrizeNumberList)
            }
        }
    }

    override fun getHistoryData() {
        viewModelScope.launch {
            val timeList = withContext(Dispatchers.IO) {
                HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.queryAllHistoryTime()
            }
            mHistorySelectList.clear()
            mHistorySelectList.addAll(timeList)
            mHistoryTimeList.emit(mHistorySelectList)
        }
    }

    override fun insertHistoryData(selectNumberList: ArrayList<SelectNumber>) {
        viewModelScope.launch {
            val historyRecord = when (mHistoryPrizeNumberList.size) {
                0 -> {
                    HistoryRecord(System.currentTimeMillis(), 0, selectNumberList)
                }
                else -> {
                    HistoryRecord(
                        System.currentTimeMillis(),
                        mHistoryPrizeNumberList[0].prizeDesignatedTime+1,
                        selectNumberList
                    )
                }
            }

            val id = withContext(Dispatchers.IO) {
                HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.insertHistoryTimeData(
                    historyRecord
                )
            }

            if (id >= 0) {
                historyRecord.id = id
                mHistorySelectList.add(historyRecord)
                mHistoryTimeList.emit(mHistorySelectList)
            }
        }

    }

    /**
     * @param fileUrl 下载完整url
     * @param destFileDir  SD路径
     * @param destFileName  文件名
     */
    private fun downloadFile(
        fileUrl: String?,
        destFileDir: String?,
        destFileName: String
    ) {
        OkGo.get<File>(fileUrl).tag(MyApplication.mContext)
            .execute(object : FileCallback(destFileDir, destFileName) {
                //文件下载时指定下载的路径以及下载的文件的名称
                override fun onStart(request: Request<File?, out Request<*, *>?>?) {
                    super.onStart(request)
                    Log.d("zhanghao", "开始下载文件")
                }

                override fun onSuccess(response: Response<File>) {
                    val mBasePath = response.body().absolutePath
                    Log.d("zhanghao", "下载文件成功:$mBasePath")
                    readTxt(mBasePath)
                }

                override fun onFinish() {
                    super.onFinish()
                    Log.d("zhanghao", "下载文件完成")
                }

                override fun onError(response: Response<File?>) {
                    super.onError(response)
                    Log.e("zhanghao", "下载文件出错:" + response.message())
                }

                override fun downloadProgress(progress: Progress) {
                    super.downloadProgress(progress)
                    val dLProgress = progress.fraction
                    Log.d("zhanghao", "文件下载的进度:$dLProgress")
                }
            })
    }

    fun readTxt(path: String) {
        val prizeNumberList = ArrayList<HistoryPrizeNumber>()
        try {
            val urlFile = File(path)
            val isr = InputStreamReader(FileInputStream(urlFile), "UTF-8")
            val br = BufferedReader(isr)
            var mimeTypeLine: String
            while (br.readLine().also { mimeTypeLine = it } != null) {
                val list = mimeTypeLine.split(" ")
                if (list.size >= 9) {
                    val prizeDesignatedTime = list[0].toLong()
                    if (mHistoryPrizeNumberList.size > 0 && mHistoryPrizeNumberList[0].prizeDesignatedTime >= prizeDesignatedTime) {
                        break
                    }
                    val prizeDateTime = list[1]
                    val blueList = ArrayList<Int>()
                    for (index in 0 until 6) {
                        blueList.add(list[index + 2].toInt())
                    }
                    val redList = ArrayList<Int>()
                    redList.add(list[8].toInt())
                    val mSelectNumber = SelectNumber(blueList, redList)
                    val historyPrizeNumber =
                        HistoryPrizeNumber(prizeDateTime, prizeDesignatedTime, mSelectNumber)
                    prizeNumberList.add(historyPrizeNumber)
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.insertHistoryPrizeData(
            prizeNumberList
        )
        mHistoryPrizeNumberList.addAll(prizeNumberList)
        mHistoryPrizeNumberList.sortByDescending { historyPrizeNumber -> historyPrizeNumber.prizeDesignatedTime }

        dealHistoryPrizeNumber(DefCommonUtils.SHUANG_SE_QIU_BLUE_NUMBER,DefCommonUtils.SHUANG_SE_QIU_RED_NUMBER)

        viewModelScope.launch {
            mHistoryPrizeList.emit(mHistoryPrizeNumberList)
        }
    }

    override fun deleteHistorySelectList(mBaseHistoryRecordList: ArrayList<BaseHistoryRecord>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    mBaseHistoryRecordList.forEach {
                        HistoryDataBase.getInstance(MyApplication.mContext).mHistoryDao.deleteHistoryTimeData(
                            it as HistoryRecord
                        )
                        mHistorySelectList.remove(it)
                    }

                }

                mHistoryTimeList.emit(mHistorySelectList)
            }
        }
    }

}