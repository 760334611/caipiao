package com.example.caipiao.common.fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.caipiao.databinding.FragmentHistoryNumberBinding
import com.example.caipiao.common.adapter.HistoryTimeAdapter
import com.example.caipiao.common.bean.BaseHistoryRecord
import com.example.caipiao.common.viewmodel.BaseCommonViewModel
import kotlinx.coroutines.launch


class HistoryNumberFragment(model: BaseCommonViewModel, lottery: String) :
    BaseFragment(model, lottery) {

    val selectDeleteList = ArrayList<BaseHistoryRecord>()

    private val mBinding: FragmentHistoryNumberBinding by lazy {
        FragmentHistoryNumberBinding.inflate(layoutInflater)
    }

    private val mHistoryTimeAdapter: HistoryTimeAdapter by lazy {
        HistoryTimeAdapter()
    }

    override fun initRoot(): View {
        return mBinding.root
    }


    override fun initData() {
        super.initData()
        mBinding.recycleTime.adapter = mHistoryTimeAdapter
        mBinding.onClickHandler = this
        mHistoryTimeAdapter.run {
            itemClick = {
                mFileFragmentListener?.onSwitchFragmentLottery(
                    it.selectNumberList,
                    it.prizeDesignatedTime
                )
            }
            itemSelectDelete = { isCheck, mBaseHistoryRecord ->
                if (isCheck) {
                    selectDeleteList.add(mBaseHistoryRecord)
                } else {
                    selectDeleteList.remove(mBaseHistoryRecord)
                }
            }
            itemLongClick = {
                selectDeleteList.clear()
                mBinding.delete.visibility = View.VISIBLE
            }
        }
        lifecycleScope.launchWhenCreated {
            launch {
                mBaseCommonViewModel.mHistoryTimeList.collect {
                    mBinding.delete.visibility = View.GONE
                    mHistoryTimeAdapter.setDeleteState(false)
                    mHistoryTimeAdapter.setData(it)
                }
            }
        }
        mHistoryTimeAdapter.setData(mBaseCommonViewModel.getHistorySelectList())
    }

    fun delete() {
        if (selectDeleteList.size > 0) {
            mBaseCommonViewModel.deleteHistorySelectList(selectDeleteList)
        } else {
            mBinding.delete.visibility = View.GONE
            mHistoryTimeAdapter.setDeleteState(false)
            mHistoryTimeAdapter.notifyDataSetChanged()
        }

    }
}