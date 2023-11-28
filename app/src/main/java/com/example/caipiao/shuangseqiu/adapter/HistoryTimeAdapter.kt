package com.example.caipiao.shuangseqiu.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.shuangseqiu.bean.HistoryRecord
import java.text.SimpleDateFormat
import java.util.*

class HistoryTimeAdapter : RecyclerView.Adapter<HistoryTimeAdapter.HistoryTimeHolder>() {

    private val timeList = ArrayList<HistoryRecord>()
    private var weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var itemClick: ((HistoryRecord) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryTimeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_time, parent, false)
        return HistoryTimeHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryTimeHolder, position: Int) {
        holder.buttonTime.text = getDateToString(timeList[position].time)
        holder.itemView.setOnClickListener {
            itemClick?.invoke(timeList[position])
        }
    }

    override fun getItemCount(): Int {
        return timeList.size
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

    fun setData(list: List<HistoryRecord>) {
        timeList.clear()
        timeList.addAll(list)
        timeList.sortByDescending { historyRecord -> historyRecord.time }
        notifyDataSetChanged()
    }

    inner class HistoryTimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val buttonTime: Button = view.findViewById(R.id.btn_history_time)
    }

}