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

    private val numberList = ArrayList<SelectNumber>()


    fun autoSelectNumber(number:Int){
        viewModelScope.launch {
            for(i in 0 until number){
                numberList.add(randomBall())
            }
            mSelectNumberList.emit(numberList)
        }
    }



    /**
     * 机选号码
     *
     * @param list
     * @param num     机选几个
     * @param ballNum 在多少个数里选
     */
    fun randomBall(): SelectNumber {
        val selectNumber = SelectNumber()
        val randomBlue = Random()
        val boolBlue = BooleanArray(33)
        var randInt = 0
        for (i in 0 until 6) {
            do {
                randInt = randomBlue.nextInt(33)
            } while (boolBlue[randInt])
            boolBlue[randInt] = true
            when(i){
                0->selectNumber.blueOne=randInt
                1->selectNumber.blueTwo=randInt
                2->selectNumber.blueThree=randInt
                3->selectNumber.blueFour=randInt
                4->selectNumber.blueFive=randInt
                5->selectNumber.blueSix=randInt
            }
        }
        randInt = randomBlue.nextInt(16)
        selectNumber.redOne=randInt
        return selectNumber
    }


}