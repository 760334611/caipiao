package com.example.caipiao.shuangseqiu.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.caipiao.R
import com.example.caipiao.common.FileFragmentListener
import com.example.caipiao.databinding.LotteryResultsFragmentBinding
import com.example.caipiao.shuangseqiu.adapter.LotteryResultsAdapter
import com.example.caipiao.shuangseqiu.bean.HistoryPrizeNumber
import com.example.caipiao.shuangseqiu.bean.SelectNumber
import com.example.caipiao.shuangseqiu.viewmodel.MainShuangSeQiuViewModel
import java.text.SimpleDateFormat
import java.util.*


class LotteryResultsFragment : Fragment() {

    var mFileFragmentListener: FileFragmentListener? = null
    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat("yyyy-MM-dd")
    private val mBinding: LotteryResultsFragmentBinding by lazy {
        LotteryResultsFragmentBinding.inflate(layoutInflater)
    }

    private val mLotteryResultsAdapter: LotteryResultsAdapter by lazy {
        LotteryResultsAdapter()
    }
    private val mMainShuangSeQiuViewModel: MainShuangSeQiuViewModel by activityViewModels()

    companion object {
        @JvmStatic
        fun newInstance(): LotteryResultsFragment {
            return LotteryResultsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        mBinding.onClickHandler = this
        mBinding.selectNumberRecycler.adapter = mLotteryResultsAdapter

    }

    @SuppressLint("SetTextI18n")
    fun setSelectNumberList(list: List<SelectNumber>, prizeDesignatedTime: Long) {
        var mHistoryPrizeNumber: HistoryPrizeNumber? = null

        run breaking@{
            mMainShuangSeQiuViewModel.getHistoryPrizeNumberList().forEach {
                if (prizeDesignatedTime == it.prizeDesignatedTime) {
                    mHistoryPrizeNumber = it
                    return@breaking
                }
            }
        }

        if(mHistoryPrizeNumber!=null){
            val date = Date(mHistoryPrizeNumber!!.prizeDateTime)
            mBinding.tvTime.text = format.format(date)
            mBinding.tvDesignated.text = "第${mHistoryPrizeNumber!!.prizeDesignatedTime.toString()}期"
            mBinding.llContainer.removeAllViews()
            mHistoryPrizeNumber!!.mSelectNumber.blueList.forEach {
                getNumberView(R.mipmap.blue_circle, it)
            }
            mHistoryPrizeNumber!!.mSelectNumber.redList.forEach {
                getNumberView(R.mipmap.red_circle, it)
            }
        }else{
            mBinding.llContainer.removeAllViews()
            mBinding.tvDesignated.text = "第${prizeDesignatedTime.toString()}期"
            mBinding.tvTime.text="未开奖"
        }
        mLotteryResultsAdapter.setData(list, mHistoryPrizeNumber?.mSelectNumber)
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
        layoutParams.weight= 1F
        mBinding.llContainer.addView(viewNumber,layoutParams)
    }
}