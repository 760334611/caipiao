package com.example.caipiao.common.fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.caipiao.databinding.FragmentHistoryNumberBinding
import com.example.caipiao.common.adapter.HistoryTimeAdapter
import com.example.caipiao.common.viewmodel.BaseCommonViewModel
import kotlinx.coroutines.launch


class HistoryNumberFragment(model: BaseCommonViewModel, lottery:String) : BaseFragment(model,lottery) {

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
        mHistoryTimeAdapter.run {
            itemClick = {
                mFileFragmentListener?.onSwitchFragmentLottery(it.selectNumberList,it.prizeDesignatedTime)
            }
        }
        lifecycleScope.launchWhenCreated {
            launch {
                mBaseCommonViewModel.mHistoryTimeList.collect {
                    mHistoryTimeAdapter.setData(it)
                }
            }
        }
        mHistoryTimeAdapter.setData(mBaseCommonViewModel.getHistorySelectList())
    }
}