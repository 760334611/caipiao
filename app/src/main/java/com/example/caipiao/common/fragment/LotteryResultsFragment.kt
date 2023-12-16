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
import com.example.caipiao.common.dialog.LotteryResultsDialog
import com.example.caipiao.common.viewmodel.BaseCommonViewModel
import java.util.*
import kotlin.collections.ArrayList


class LotteryResultsFragment(model: BaseCommonViewModel, lottery: String) :
    BaseFragment(model, lottery) {
    private val mBinding: LotteryResultsFragmentBinding by lazy {
        LotteryResultsFragmentBinding.inflate(layoutInflater)
    }
    private var isLottery: Boolean = false
    private var selectNumberList = ArrayList<SelectNumber>()
    private var mHistoryPrizeNumber: BaseHistoryPrizeNumber? = null
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

    override fun initView() {
        super.initView()
        mBinding.clLotteryResults.setOnLongClickListener {
            val mSelectDialog = LotteryResultsDialog(context, R.style.base_BaseDialog)
            mSelectDialog.setLotteryResultsData(mBaseCommonViewModel.mHistoryPrizeNumberList)
            mSelectDialog.run {
                itemLotteryClick = {
                    mHistoryPrizeNumber=it
                    showLotteryResults(mHistoryPrizeNumber)
                }
            }
            mSelectDialog.show()
            false
        }
        mBinding.closeLotteryResults.setOnClickListener {
            mBinding.getPrize.visibility=View.VISIBLE
            mBinding.clLotteryResults.visibility=View.GONE
            isLottery=false
            mLotteryResultsAdapter.setData(
                selectNumberList,
                SelectNumber(),
                lotteryType,
                isLottery
            )
        }
        mBinding.getPrize.setOnClickListener {
            mBinding.getPrize.visibility=View.GONE
            mBinding.clLotteryResults.visibility=View.VISIBLE
            showLotteryResults(mHistoryPrizeNumber)
        }
    }

    @SuppressLint("SetTextI18n")
    fun setSelectNumberList(list: List<SelectNumber>) {
        mBinding.getPrize.visibility=View.VISIBLE
        mBinding.clLotteryResults.visibility=View.GONE
        isLottery=false

        if (mBaseCommonViewModel.getHistoryPrizeNumberList().size > 0) {
            mHistoryPrizeNumber = mBaseCommonViewModel.getHistoryPrizeNumberList()[0]
        }
        selectNumberList.clear()
        selectNumberList.addAll(list)
        mLotteryResultsAdapter.setData(
            selectNumberList,
            SelectNumber(),
            lotteryType,
            isLottery
        )
    }

    @SuppressLint("SetTextI18n")
    private fun showLotteryResults(
        mHistoryPrizeNumber: BaseHistoryPrizeNumber?
    ) {
        if (mHistoryPrizeNumber != null) {
            mBinding.tvTime.text = mHistoryPrizeNumber.prizeDateTime
            mBinding.tvDesignated.text = "第${mHistoryPrizeNumber.prizeDesignatedTime}期"
            mBinding.llContainer.removeAllViews()
            mHistoryPrizeNumber.mSelectNumber.blueList.forEach {
                getNumberView(R.mipmap.blue_circle, it)
            }
            mHistoryPrizeNumber.mSelectNumber.redList.forEach {
                getNumberView(R.mipmap.red_circle, it)
            }
            isLottery = true
        } else {
            mBinding.llContainer.removeAllViews()
            mBinding.tvDesignated.text = "第0000000期"
            mBinding.tvTime.text = "未开奖"
            isLottery = false
        }
        mLotteryResultsAdapter.setData(
            selectNumberList,
            mHistoryPrizeNumber?.mSelectNumber,
            lotteryType,
            isLottery
        )
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