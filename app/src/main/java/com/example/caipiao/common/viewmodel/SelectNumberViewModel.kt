package com.example.caipiao.common.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caipiao.common.DefCommonUtils
import com.example.caipiao.common.bean.SelectNumber
import com.example.caipiao.common.bean.WeightSortBean
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SelectNumberViewModel : ViewModel() {
    var mSelectNumberList: MutableSharedFlow<ArrayList<SelectNumber>> =
        MutableSharedFlow()

    private var numberList = ArrayList<SelectNumber>()

    private var blueTotalNumber = 33
    private var blueSelectableNumber = 6
    private var redTotalNumber = 16
    private var redSelectableNumber = 1
    private var selectBlueTotalMap = HashMap<Int, WeightSortBean>()
    private var selectRedTotalMap = HashMap<Int, WeightSortBean>()


    fun autoSelectNumber(number: Int) {
        viewModelScope.launch {
            for (i in 0 until number) {
                numberList.add(randomBall())
            }
            mSelectNumberList.emit(numberList)
        }
    }

    fun uploadNumberList(list: ArrayList<SelectNumber>) {
        viewModelScope.launch {
            numberList.clear()
            numberList.addAll(list)
            mSelectNumberList.emit(numberList)
        }
    }

    fun setLotteryType(lotteryType: String) {
        when (lotteryType) {
            DefCommonUtils.LOTTERY_TYPE_SHUANG -> {
                this.blueSelectableNumber = 6
                this.redSelectableNumber = 1
            }
            DefCommonUtils.LOTTERY_TYPE_DA -> {
                this.blueSelectableNumber = 5
                this.redSelectableNumber = 2
            }
        }

    }

    fun setLotteryWeightData(
        selectBlueTotalMap: HashMap<Int, WeightSortBean>,
        selectRedTotalMap: HashMap<Int, WeightSortBean>,
        blueTotalNumber: Int,
        redTotalNumber: Int
    ) {
        this.selectBlueTotalMap = selectBlueTotalMap
        this.selectRedTotalMap = selectRedTotalMap
        this.blueTotalNumber = blueTotalNumber
        this.redTotalNumber = redTotalNumber
        Log.i("zhanghao", "blueTotalNumber=$blueTotalNumber")
        Log.i("zhanghao", "redTotalNumber=$redTotalNumber")
    }

    fun getSelectNumberList() = numberList


    /**
     * 机选号码
     */
    private fun randomBall(): SelectNumber {

        val randomBlue = Random()
        val boolBlue = BooleanArray(blueTotalNumber+1)
        val blueNumber = ArrayList<Int>()
        var blueInt: Int
        for (i in 0 until blueSelectableNumber) {
            do {
                blueInt = randomBlue.nextInt(blueTotalNumber)
            } while (boolBlue[blueInt])
            run breaking@{
                selectBlueTotalMap.forEach { (key, value) ->
                    if (value.startWeight < blueInt && blueInt <= value.endWeight) {
                        for (k in value.startWeight..value.endWeight) {
                            if (k >= 0) {
                                boolBlue[k] = true
                            }

                        }
                        blueNumber.add(key)
                        return@breaking
                    }
                }
            }

        }
        blueNumber.sort()

        val redNumber = ArrayList<Int>()
        val boolRed = BooleanArray(redTotalNumber+1)
        var redInt: Int
        for (i in 0 until redSelectableNumber) {
            do {
                redInt = randomBlue.nextInt(redTotalNumber)
            } while (boolRed[redInt])
            run breaking@{
                selectRedTotalMap.forEach { (key, value) ->
                    if (value.startWeight < redInt && redInt <= value.endWeight) {
                        for (k in value.startWeight..value.endWeight) {
                            if (k >= 0) {
                                boolRed[k] = true
                            }
                        }
                        redNumber.add(key)
                        return@breaking
                    }
                }
            }
        }
        redNumber.sort()
        return SelectNumber(blueNumber, redNumber)
    }


}