package com.example.caipiao.shuangseqiu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.shuangseqiu.bean.SelectNumber
import java.util.*

class SelectNumberAdapter : RecyclerView.Adapter<SelectNumberAdapter.SelectNumberHolder>() {

    private val numberList = ArrayList<SelectNumber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectNumberHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_select_shuang_number, parent, false)
        return SelectNumberHolder(view)
    }

    override fun onBindViewHolder(holder: SelectNumberHolder, position: Int) {
        holder.numberOne.text = numberList[position].blueOne.toString()
        holder.numberTwo.text = numberList[position].blueTwo.toString()
        holder.numberThree.text = numberList[position].blueThree.toString()
        holder.numberFour.text = numberList[position].blueFour.toString()
        holder.numberFive.text = numberList[position].blueFive.toString()
        holder.numberSix.text = numberList[position].blueSix.toString()
        holder.numberSeven.text = numberList[position].redOne.toString()
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    fun setData(list: List<SelectNumber>) {
        numberList.clear()
        numberList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SelectNumberHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberOne: TextView = view.findViewById(R.id.number_one)
        val numberTwo: TextView = view.findViewById(R.id.number_two)
        val numberThree: TextView = view.findViewById(R.id.number_three)
        val numberFour: TextView = view.findViewById(R.id.number_four)
        val numberFive: TextView = view.findViewById(R.id.number_five)
        val numberSix: TextView = view.findViewById(R.id.number_six)
        val numberSeven: TextView = view.findViewById(R.id.number_seven)
    }

}