package com.example.caipiao.common

abstract class FileFragmentListener {
    /**
     * 关闭Activity
     */
    abstract fun onActivityFinish()

    /**
     * 切换Fragment后退
     */
    abstract fun onSwitchFragmentBack(tag:String)

    /**
     * 切换Fragment前进
     */
    abstract fun onSwitchFragmentForward(tag:String)

    /**
     * 切换Fragment前进
     */
    abstract fun onSwitchFragmentForward(tagFrom:String,tagTo:String)
}