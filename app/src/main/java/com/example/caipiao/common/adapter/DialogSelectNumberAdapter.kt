package com.example.caipiao.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import kotlin.collections.ArrayList

class DialogSelectNumberAdapter :
    RecyclerView.Adapter<DialogSelectNumberAdapter.SelectNumberHolder>() {

    private var numberBall: Int = 33
    private var typeBall: Int = 1
    private var limitNumber: Int = 6
    private var isEnable: Boolean = true
    private var numberList = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectNumberHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dialog_number, parent, false)
        return SelectNumberHolder(view)
    }

    override fun onBindViewHolder(holder: SelectNumberHolder, position: Int) {
        if (typeBall == 1) {
            holder.checkBox.setBackgroundResource(R.mipmap.blue_circle)
        } else {
            holder.checkBox.setBackgroundResource(R.mipmap.red_circle)
        }
        holder.checkBox.text = (position + 1).toString()


        if (numberList.contains(position + 1)) {
            holder.checkBox.alpha = 1f
            holder.checkBox.isEnabled = true
            holder.checkBox.isChecked = true
        } else {
            holder.checkBox.alpha = 0.4f
            holder.checkBox.isEnabled = isEnable
            holder.checkBox.isChecked = false
        }
        holder.checkBox.setOnCheckedChangeListener { buttonView, isCheck ->
            if (buttonView.isPressed) {
                if (isCheck) {
                    holder.checkBox.alpha = 1f
                    if (!numberList.contains(position + 1)) {
                        numberList.add(position + 1)
                    }
                    if (numberList.size >= limitNumber) {
                        isEnable = false;
                        notifyDataSetChanged()
                    }
                } else {
                    holder.checkBox.alpha = 0.4f
                    if (numberList.contains(position + 1)) {
                        if (numberList.size >= limitNumber) {
                            numberList.remove(position + 1)
                            isEnable = true;
                            notifyDataSetChanged()
                        } else {
                            numberList.remove(position + 1)
                        }
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return numberBall
    }

    /**
     * type:1=蓝色，2=红色
     */
    fun setData(number: Int, limit: Int, type: Int) {
        numberBall = number
        typeBall = type
        limitNumber = limit
    }

    fun setSelectData(list: ArrayList<Int>) {
        numberList.clear()
        numberList.addAll(list)
        isEnable = numberList.size < limitNumber
        notifyDataSetChanged()
    }

    fun getSelectData(): ArrayList<Int> {
        numberList.sort()
        return numberList
    }

    inner class SelectNumberHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.check_select_number)
    }

}