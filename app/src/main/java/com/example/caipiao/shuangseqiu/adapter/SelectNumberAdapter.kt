package com.example.caipiao.shuangseqiu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.shuangseqiu.bean.SelectNumber
import com.example.caipiao.shuangseqiu.dialog.SelectDialog
import kotlin.collections.ArrayList

class SelectNumberAdapter : RecyclerView.Adapter<SelectNumberAdapter.SelectNumberHolder>() {

    private val numberList = ArrayList<SelectNumber>()
    var uploadList: ((ArrayList<SelectNumber>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectNumberHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_select_shuang_number, parent, false)
        return SelectNumberHolder(view)
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onBindViewHolder(holder: SelectNumberHolder, position: Int) {
        holder.constraintLayout.removeAllViews()
        numberList[position].blueList.forEach {
            getNumberView(holder.itemView.context, holder, R.mipmap.blue_circle, it)

        }

        numberList[position].redList.forEach {
            getNumberView(holder.itemView.context, holder, R.mipmap.red_circle, it)
        }

        holder.itemView.setOnLongClickListener {
            val mSelectDialog = SelectDialog(holder.itemView.context, R.style.base_BaseDialog)
            mSelectDialog.setSelectDataLimit(33, 16, 6, 1)
            mSelectDialog.setSelectData(numberList[position].blueList, numberList[position].redList)
            mSelectDialog.run {
                itemDelete = {
                    numberList.remove(numberList[position])
                    uploadList?.invoke(numberList)
                    notifyDataSetChanged()
                }
                itemUpload = { blueList, redList ->
                    numberList[position].setListData(blueList, redList)
                    uploadList?.invoke(numberList)
                    notifyDataSetChanged()
                }
            }
            mSelectDialog.show()
            false
        }
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun getNumberView(
        context: Context,
        holder: SelectNumberHolder,
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
        holder.constraintLayout.addView(viewNumber, layoutParams)
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
        val constraintLayout: LinearLayout = view.findViewById(R.id.constraintLayout)
    }

}