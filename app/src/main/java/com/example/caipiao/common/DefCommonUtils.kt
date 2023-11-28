package com.example.caipiao.common

import android.os.Environment
import com.example.caipiao.MyApplication
import java.io.File

class DefCommonUtils {
    companion object{
        var SHUANG_SE_QIU_URL:String="http://data.17500.cn/ssq_desc.txt"
        var SHUANG_SE_QIU_FILE:String= getBaseDir() + "/download/"
        var SHUANG_SE_QIU_NAME:String= "/shuangseqiu.txt/"

        private fun getBaseDir(): String {
            val externalStoragePublicDirectory: String =
                MyApplication.mContext.getExternalFilesDir("")?.absolutePath ?:Environment.getExternalStorageDirectory().absolutePath// Environment.MEDIA_MOUNTEDEnvironment.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            val dir = File(externalStoragePublicDirectory)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return externalStoragePublicDirectory
        }
    }
}