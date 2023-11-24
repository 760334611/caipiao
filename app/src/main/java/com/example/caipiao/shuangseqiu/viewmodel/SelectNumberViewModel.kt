package com.example.caipiao.shuangseqiu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caipiao.shuangseqiu.bean.SelectNumber
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SelectNumberViewModel : ViewModel() {
    var mSelectNumberList: MutableSharedFlow<ArrayList<SelectNumber>> =
        MutableSharedFlow()

    private var numberList = ArrayList<SelectNumber>()


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


    /**
     * 机选号码
     */
    private fun randomBall(): SelectNumber {

        val randomBlue = Random()
        val boolBlue = BooleanArray(34)
        val blueNumber = ArrayList<Int>()
        val redNumber = ArrayList<Int>()
        var randInt: Int
        for (i in 0 until 6) {
            do {
                randInt = randomBlue.nextInt(33) + 1
            } while (boolBlue[randInt])
            boolBlue[randInt] = true
            blueNumber.add(randInt)
        }
        blueNumber.sort()
        randInt = randomBlue.nextInt(16) + 1
        redNumber.add(randInt)
        return SelectNumber(blueNumber, redNumber)
    }


}