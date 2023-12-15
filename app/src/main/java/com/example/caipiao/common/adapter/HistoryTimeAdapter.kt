package com.example.caipiao.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.common.bean.BaseHistoryRecord
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryTimeAdapter : RecyclerView.Adapter<HistoryTimeAdapter.HistoryTimeHolder>() {

    private val timeList = ArrayList<BaseHistoryRecord>()
    private var weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private var isDelete: Boolean = false
    var itemClick: ((BaseHistoryRecord) -> Unit)? = null
    var itemSelectDelete: ((Boolean, BaseHistoryRecord) -> Unit)? = null
    var itemLongClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryTimeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_time, parent, false)
        return HistoryTimeHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryTimeHolder, position: Int) {
        holder.tvTime.text = getDateToString(timeList[position].time)
        holder.tvTime.setOnClickListener {
            itemClick?.invoke(timeList[position])
        }
        holder.tvTime.setOnLongClickListener {
            if (!isDelete) {
                isDelete = true
                notifyDataSetChanged()
                itemLongClick?.invoke()
            }
            false
        }
        if (isDelete) {
            holder.checkBox.visibility = View.VISIBLE
        } else {
            holder.checkBox.visibility = View.GONE
            holder.checkBox.isChecked = false
        }
        holder.checkBox.setOnCheckedChangeListener { compoundButton, isCheck ->
            if (compoundButton.isPressed) {
                itemSelectDelete?.invoke(isCheck, timeList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    fun setDeleteState(deleteState:Boolean) {
        isDelete=deleteState
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateToString(milSecond: Long): String {
        val date = Date(milSecond)

        val cal = Calendar.getInstance()
        cal.time = date
        var w = cal[Calendar.DAY_OF_WEEK] - 1
        if (w < 0) w = 0

        return weekDays[w] + " " + format.format(date)
    }

    fun setData(list: ArrayList<BaseHistoryRecord>) {
        timeList.clear()
        timeList.addAll(list)
        timeList.sortByDescending { historyRecord -> historyRecord.time }
        notifyDataSetChanged()
    }

    inner class HistoryTimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tv_history_time)
        val checkBox: CheckBox = view.findViewById(R.id.check_box)
    }

}