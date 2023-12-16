package com.example.caipiao.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.common.bean.BaseHistoryPrizeNumber
import kotlin.collections.ArrayList

class LotteryResultsNumberAdapter :
    RecyclerView.Adapter<LotteryResultsNumberAdapter.SelectNumberHolder>() {
    var itemClick: ((BaseHistoryPrizeNumber) -> Unit)? = null

    private var historyPrizeNumberList = ArrayList<BaseHistoryPrizeNumber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectNumberHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_lottery_results_date, parent, false)
        return SelectNumberHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SelectNumberHolder, position: Int) {
        val mHistoryPrizeNumber = historyPrizeNumberList[position]
        holder.tvLotteryTime.text=mHistoryPrizeNumber.prizeDateTime
        holder.tvLotteryDesignated.text="第${mHistoryPrizeNumber.prizeDesignatedTime}期"
        holder.itemView.setOnClickListener {
            itemClick?.invoke(mHistoryPrizeNumber)
        }
    }

    override fun getItemCount(): Int {
        return historyPrizeNumberList.size
    }


    fun setLotteryResultsData(list: ArrayList<BaseHistoryPrizeNumber>) {
        historyPrizeNumberList.clear()
        historyPrizeNumberList.addAll(list)
    }


    inner class SelectNumberHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLotteryDesignated: TextView = view.findViewById(R.id.tv_lottery_designated)
        val tvLotteryTime: TextView = view.findViewById(R.id.tv_lottery_time)
    }

}