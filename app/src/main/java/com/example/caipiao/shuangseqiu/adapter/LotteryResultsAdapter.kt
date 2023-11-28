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

class LotteryResultsAdapter : RecyclerView.Adapter<LotteryResultsAdapter.SelectNumberHolder>() {

    private val numberList = ArrayList<SelectNumber>()
    private val mSelectNumber: SelectNumber = SelectNumber()
    var uploadList: ((ArrayList<SelectNumber>) -> Unit)? = null

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
        if (blueLotteryNumber == 6 && redLotteryNumber == 1) {
            holder.tvLotteryResults.text = "一等奖500万元"
        } else if (blueLotteryNumber == 6) {
            holder.tvLotteryResults.text = "二等奖80万元"
        } else if (blueLotteryNumber == 5 && redLotteryNumber == 1) {
            holder.tvLotteryResults.text = "三等奖3000元"
        } else if ((blueLotteryNumber == 5) || (blueLotteryNumber == 4 && redLotteryNumber == 1)) {
            holder.tvLotteryResults.text = "四等奖200元"
        } else if ((blueLotteryNumber == 4) || (blueLotteryNumber == 3 && redLotteryNumber == 1)) {
            holder.tvLotteryResults.text = "五等奖10元"
        } else if (redLotteryNumber == 1) {
            holder.tvLotteryResults.text = "六等奖5元"
        } else {
            holder.tvLotteryResults.text = "未中奖"
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
        var isLottery = false
        val viewNumber: View =
            LayoutInflater.from(context)
                .inflate(R.layout.item_number, null)
        val selectNumber = viewNumber.findViewById<TextView>(R.id.tv_select_number)
        if (type == 1) {
            selectNumber.setBackgroundResource(R.mipmap.blue_circle)
            if (mSelectNumber.blueList.contains(it)) {
                selectNumber.alpha = 1f
                isLottery = true
            } else {
                selectNumber.alpha = 0.4f
                isLottery = false
            }
        } else {
            selectNumber.setBackgroundResource(R.mipmap.red_circle)
            if (mSelectNumber.redList.contains(it)) {
                selectNumber.alpha = 1f
                isLottery = true
            } else {
                selectNumber.alpha = 0.4f
                isLottery = false
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

    fun setData(list: List<SelectNumber>, mSelectNumber: SelectNumber?) {
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