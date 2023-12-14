package com.example.caipiao.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.common.adapter.LotteryResultsNumberAdapter
import com.example.caipiao.common.bean.BaseHistoryPrizeNumber

class LotteryResultsDialog(context: Context?, themeResId: Int) : Dialog(context!!, themeResId) {

    var itemLotteryClick: ((BaseHistoryPrizeNumber) -> Unit)? = null

    private val mLotteryResultsNumberAdapter: LotteryResultsNumberAdapter by lazy {
        LotteryResultsNumberAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lottery_results_number)
        val lotteryResultsRecycle = findViewById<RecyclerView>(R.id.lottery_results_recycle)
        lotteryResultsRecycle.adapter = mLotteryResultsNumberAdapter

        mLotteryResultsNumberAdapter.run {
            itemClick={
                itemLotteryClick?.invoke(it)
                dismiss()
            }
        }
    }

    fun setLotteryResultsData(list: ArrayList<BaseHistoryPrizeNumber>) {
        mLotteryResultsNumberAdapter.setLotteryResultsData(list)
    }
}