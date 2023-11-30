package com.example.caipiao.common.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.caipiao.R
import com.example.caipiao.common.bean.BaseHistoryPrizeNumber
import com.example.caipiao.databinding.LotteryResultsFragmentBinding
import com.example.caipiao.common.adapter.LotteryResultsAdapter
import com.example.caipiao.common.bean.SelectNumber
import com.example.caipiao.common.viewmodel.BaseCommonViewModel
import java.text.SimpleDateFormat
import java.util.*


class LotteryResultsFragment(model: BaseCommonViewModel, lottery:String) : BaseFragment(model,lottery) {

    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat("yyyy-MM-dd")
    private val mBinding: LotteryResultsFragmentBinding by lazy {
        LotteryResultsFragmentBinding.inflate(layoutInflater)
    }
    private var isLottery:Boolean=false

    private val mLotteryResultsAdapter: LotteryResultsAdapter by lazy {
        LotteryResultsAdapter()
    }

    override fun initRoot(): View {
        return mBinding.root
    }

    override fun initData() {
        super.initData()
        mBinding.selectNumberRecycler.adapter = mLotteryResultsAdapter
    }

    @SuppressLint("SetTextI18n")
    fun setSelectNumberList(list: List<SelectNumber>, prizeDesignatedTime: Long) {
        var mHistoryPrizeNumber: BaseHistoryPrizeNumber? = null

        run breaking@{
            mBaseCommonViewModel.getHistoryPrizeNumberList().forEach {
                if (prizeDesignatedTime == it.prizeDesignatedTime) {
                    mHistoryPrizeNumber = it
                    return@breaking
                }
            }
        }

        if (mHistoryPrizeNumber != null) {
            val date = Date(mHistoryPrizeNumber!!.prizeDateTime)
            mBinding.tvTime.text = format.format(date)
            mBinding.tvDesignated.text = "第${mHistoryPrizeNumber!!.prizeDesignatedTime}期"
            mBinding.llContainer.removeAllViews()
            mHistoryPrizeNumber!!.mSelectNumber.blueList.forEach {
                getNumberView(R.mipmap.blue_circle, it)
            }
            mHistoryPrizeNumber!!.mSelectNumber.redList.forEach {
                getNumberView(R.mipmap.red_circle, it)
            }
            isLottery=true
        } else {
            mBinding.llContainer.removeAllViews()
            mBinding.tvDesignated.text = "第${prizeDesignatedTime}期"
            mBinding.tvTime.text = "未开奖"
            isLottery=false
        }
        mLotteryResultsAdapter.setData(list, mHistoryPrizeNumber?.mSelectNumber,lotteryType,isLottery)
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun getNumberView(
        resId: Int,
        it: Int
    ) {
        val viewNumber: View =
            LayoutInflater.from(context)
                .inflate(R.layout.item_number, null)
        val selectNumber = viewNumber.findViewById<TextView>(R.id.tv_select_number)
        selectNumber.setBackgroundResource(resId)
        if (it < 10) {
            selectNumber.text = "0$it"
        } else {
            selectNumber.text = it.toString()
        }
        val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.weight = 1F
        mBinding.llContainer.addView(viewNumber, layoutParams)
    }
}