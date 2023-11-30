package com.example.caipiao.common

import android.os.Environment
import com.example.caipiao.MyApplication
import java.io.File

class DefCommonUtils {
    companion object{

        var LOTTERY_TYPE:String="lottery.type"
        var LOTTERY_TYPE_SHUANG:String="lottery.type.shuang.se.qiu"
        var LOTTERY_TYPE_DA:String="lottery.type.da.le.tou"

        var SHUANG_SE_QIU_URL:String="http://data.17500.cn/ssq_desc.txt"
        var SHUANG_SE_QIU_FILE:String= getBaseDir() + "/download/"
        var SHUANG_SE_QIU_NAME:String= "/shuangseqiu.txt/"

        var DA_LE_TOU_URL:String="http://data.17500.cn/dlt2_desc.txt"
        var DA_LE_TOU_FILE:String= getBaseDir() + "/download/"
        var DA_LE_TOU_NAME:String= "/daletou.txt/"

        private fun getBaseDir(): String {
            val externalStoragePublicDirectory: String =
                MyApplication.mContext.getExternalFilesDir("")?.absolutePath ?:Environment.getExternalStorageDirectory().absolutePath// Environment.MEDIA_MOUNTEDEnvironment.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            val dir = File(externalStoragePublicDirectory)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return externalStoragePublicDirectory
        }

        fun winningRules(lotteryType:String,blueLotteryNumber:Int,redLotteryNumber:Int):String{
            return when(lotteryType){
                DefCommonUtils.LOTTERY_TYPE_SHUANG->shuangSeQiu(blueLotteryNumber,redLotteryNumber)
                else ->  "未中奖"
            }
        }

        fun shuangSeQiu(blueLotteryNumber:Int,redLotteryNumber:Int):String{
            return if (blueLotteryNumber == 6 && redLotteryNumber == 1) {
                "一等奖500万元"
            } else if (blueLotteryNumber == 6) {
                "二等奖80万元"
            } else if (blueLotteryNumber == 5 && redLotteryNumber == 1) {
                "三等奖3000元"
            } else if ((blueLotteryNumber == 5) || (blueLotteryNumber == 4 && redLotteryNumber == 1)) {
                "四等奖200元"
            } else if ((blueLotteryNumber == 4) || (blueLotteryNumber == 3 && redLotteryNumber == 1)) {
                "五等奖10元"
            } else if (redLotteryNumber == 1) {
                "六等奖5元"
            } else {
                "未中奖"
            }

        }
    }
}