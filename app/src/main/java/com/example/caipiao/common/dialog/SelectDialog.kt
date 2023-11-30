package com.example.caipiao.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.example.caipiao.R
import com.example.caipiao.common.DefCommonUtils
import com.example.caipiao.common.adapter.DialogSelectNumberAdapter

class SelectDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    var itemDelete: (() -> Unit)? = null
    var itemUpload: ((ArrayList<Int>,ArrayList<Int>) -> Unit)? = null

    private val blueAdapter: DialogSelectNumberAdapter by lazy {
        DialogSelectNumberAdapter()
    }

    private val redAdapter: DialogSelectNumberAdapter by lazy {
        DialogSelectNumberAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_select_number)
        val blueRecycle = findViewById<RecyclerView>(R.id.blue_recycle)
        blueRecycle.adapter=blueAdapter
        val redRecycle = findViewById<RecyclerView>(R.id.red_recycle)
        redRecycle.adapter=redAdapter

        val delete = findViewById<Button>(R.id.delete)
        delete.setOnClickListener {
            itemDelete?.invoke()
            dismiss()
        }

        val upload = findViewById<Button>(R.id.upload)
        val groupOne: Group = findViewById(R.id.one_group)
        val groupTwo: Group = findViewById(R.id.two_group)
        upload.setOnClickListener {
            groupOne.visibility = View.GONE
            groupTwo.visibility = View.VISIBLE
        }

        val sure = findViewById<Button>(R.id.sure)
        sure.setOnClickListener {
            itemUpload?.invoke(blueAdapter.getSelectData(), redAdapter.getSelectData())
            dismiss()
        }
    }

    fun setSelectData(blueList: ArrayList<Int>,redList: ArrayList<Int>) {
        blueAdapter.setSelectData(blueList)
        redAdapter.setSelectData(redList)
    }

    fun setSelectDataLimit(lotteryType:String) {
        when(lotteryType){
            DefCommonUtils.LOTTERY_TYPE_SHUANG->{
                blueAdapter.setData(33,6,1)
                redAdapter.setData(16,1,2)
            }
            DefCommonUtils.LOTTERY_TYPE_DA->{
                blueAdapter.setData(35,5,1)
                redAdapter.setData(12,2,2)
            }
        }
    }
}