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
import com.example.caipiao.common.bean.BaseHistoryPrizeNumber
import java.text.SimpleDateFormat
import java.util.*

class HistoryPrizeAdapter : RecyclerView.Adapter<HistoryPrizeAdapter.HistoryTimeHolder>() {

    private val timeList = ArrayList<BaseHistoryPrizeNumber>()
    @SuppressLint("SimpleDateFormat")
    var itemClick: ((BaseHistoryPrizeNumber) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryTimeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_prize, parent, false)
        return HistoryTimeHolder(view)
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onBindViewHolder(holder: HistoryTimeHolder, position: Int) {
        holder.tvTime.text =timeList[position].prizeDateTime
        holder.tvDesignated.text = "第${timeList[position].prizeDesignatedTime.toString()}期"
        holder.llContainer.removeAllViews()
        timeList[position].mSelectNumber.blueList.forEach {
            getNumberView(holder.itemView.context, holder, R.mipmap.blue_circle, it)
        }

        timeList[position].mSelectNumber.redList.forEach {
            getNumberView(holder.itemView.context, holder, R.mipmap.red_circle, it)

        }

        holder.itemView.setOnClickListener {
            itemClick?.invoke(timeList[position])
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun getNumberView(
        context: Context,
        holder: HistoryTimeHolder,
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
        holder.llContainer.addView(viewNumber,layoutParams)
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    fun setData(list: List<BaseHistoryPrizeNumber>) {
        timeList.clear()
        timeList.addAll(list)
        notifyDataSetChanged()
    }

    inner class HistoryTimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llContainer: LinearLayout = view.findViewById(R.id.ll_container)
        val tvDesignated: TextView = view.findViewById(R.id.tv_designated)
        val tvTime: TextView = view.findViewById(R.id.tv_time)
    }

}