package com.example.caipiao.shuangseqiu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.shuangseqiu.bean.HistoryRecord
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

    override fun onBindViewHolder(holder: SelectNumberHolder, position: Int) {
        if (holder.constraintLayout.childCount > numberList[position].blueList.size) {
            for (index in 0 until numberList[position].blueList.size) {
                (holder.constraintLayout.getChildAt(index) as TextView).text =
                    numberList[position].blueList[index].toString()
            }
            for (index in (numberList[position].redList.size - 1) downTo 0) {
                (holder.constraintLayout.getChildAt(holder.constraintLayout.childCount - index - 1) as TextView).text =
                    numberList[position].redList[index].toString()
            }
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

    override fun getItemCount(): Int {
        return numberList.size
    }

    fun setData(list: List<SelectNumber>) {
        numberList.clear()
        numberList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SelectNumberHolder(view: View) : RecyclerView.ViewHolder(view) {
        val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)
    }

}