package com.example.caipiao.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caipiao.common.DefCommonUtils
import com.example.caipiao.common.bean.SelectNumber
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

    fun setLotteryType(lotteryType:String) {
        when(lotteryType){
            DefCommonUtils.LOTTERY_TYPE_SHUANG->{
                this.blueTotalNumber = 33
                this.blueSelectableNumber = 6
                this.redTotalNumber = 16
                this.redSelectableNumber = 1
            }
            DefCommonUtils.LOTTERY_TYPE_DA->{
                this.blueTotalNumber = 35
                this.blueSelectableNumber = 5
                this.redTotalNumber = 12
                this.redSelectableNumber = 2
            }
        }

    }

    fun getSelectNumberList() = numberList


    /**
     * 机选号码
     */
    private fun randomBall(): SelectNumber {

        val randomBlue = Random()
        val boolBlue = BooleanArray(blueTotalNumber + 1)
        val blueNumber = ArrayList<Int>()
        val redNumber = ArrayList<Int>()
        var randInt: Int
        for (i in 0 until blueSelectableNumber) {
            do {
                randInt = randomBlue.nextInt(blueTotalNumber) + 1
            } while (boolBlue[randInt])
            boolBlue[randInt] = true
            blueNumber.add(randInt)
        }
        blueNumber.sort()

        val boolRed = BooleanArray(redTotalNumber + 1)
        var redInt: Int
        for (i in 0 until redSelectableNumber) {
            do {
                redInt = randomBlue.nextInt(redTotalNumber) + 1
            } while (boolBlue[redInt])
            boolRed[redInt] = true
            redNumber.add(redInt)
        }
        redNumber.sort()

        return SelectNumber(blueNumber, redNumber)
    }


}