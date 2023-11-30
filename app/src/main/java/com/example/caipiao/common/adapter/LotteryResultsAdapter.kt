package com.example.caipiao.common.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.common.DefCommonUtils
import com.example.caipiao.common.bean.SelectNumber
import kotlin.collections.ArrayList

class LotteryResultsAdapter : RecyclerView.Adapter<LotteryResultsAdapter.SelectNumberHolder>() {

    private val numberList = ArrayList<SelectNumber>()
    private val mSelectNumber: SelectNumber = SelectNumber()
    private var lotteryType = DefCommonUtils.LOTTERY_TYPE_SHUANG
    private var isOpenLottery: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectNumberHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_lottery_results_number, parent, false)
        return SelectNumberHolder(view)
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onBindViewHolder(holder: SelectNumberHolder, position: Int) {
        var blueLotteryNumber = 0
        var redLotteryNumber = 0
        holder.constraintLayout.removeAllViews()
        numberList[position].blueList.forEach {
            if (getNumberView(holder.itemView.context, holder, 1, it)) {
                blueLotteryNumber += 1
            }
        }

        numberList[position].redList.forEach {
            if (getNumberView(holder.itemView.context, holder, 2, it)) {
                redLotteryNumber += 1
            }
        }
        if (mSelectNumber.blueList.size == 0 || mSelectNumber.redList.size == 0) {
            holder.tvLotteryResults.visibility = View.GONE
        } else {
            holder.tvLotteryResults.visibility = View.VISIBLE
        }
        holder.tvLotteryResults.text =
            DefCommonUtils.winningRules(lotteryType, blueLotteryNumber, redLotteryNumber)
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    /**
     * type:1=blue,2=red
     * return 有没有中奖
     */
    private fun getNumberView(
        context: Context,
        holder: SelectNumberHolder,
        type: Int,
        it: Int
    ): Boolean {
        val isLottery: Boolean
        val viewNumber: View =
            LayoutInflater.from(context)
                .inflate(R.layout.item_number, null)
        val selectNumber = viewNumber.findViewById<TextView>(R.id.tv_select_number)
        if (type == 1) {
            selectNumber.setBackgroundResource(R.mipmap.blue_circle)
            isLottery = mSelectNumber.blueList.contains(it)
            if (mSelectNumber.blueList.contains(it) || !isOpenLottery) {
                selectNumber.alpha = 1f
            } else {
                selectNumber.alpha = 0.4f
            }
        } else {
            selectNumber.setBackgroundResource(R.mipmap.red_circle)
            isLottery = mSelectNumber.redList.contains(it)
            if (mSelectNumber.redList.contains(it) || !isOpenLottery) {
                selectNumber.alpha = 1f
            } else {
                selectNumber.alpha = 0.4f
            }
        }

        if (it < 10) {
            selectNumber.text = "0$it"
        } else {
            selectNumber.text = it.toString()
        }

        val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.weight = 1F
        holder.constraintLayout.addView(viewNumber, layoutParams)
        return isLottery
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    fun setData(
        list: List<SelectNumber>,
        mSelectNumber: SelectNumber?,
        type: String,
        isOpenLottery: Boolean
    ) {
        lotteryType = type
        this.isOpenLottery = isOpenLottery
        numberList.clear()
        numberList.addAll(list)
        this.mSelectNumber.blueList.clear()
        this.mSelectNumber.redList.clear()
        if (mSelectNumber != null) {
            this.mSelectNumber.blueList.addAll(mSelectNumber.blueList)
            this.mSelectNumber.redList.addAll(mSelectNumber.redList)
        }
        notifyDataSetChanged()
    }

    inner class SelectNumberHolder(view: View) : RecyclerView.ViewHolder(view) {
        val constraintLayout: LinearLayout = view.findViewById(R.id.ll_container)
        val tvLotteryResults: TextView = view.findViewById(R.id.tv_lottery_results)
    }

}