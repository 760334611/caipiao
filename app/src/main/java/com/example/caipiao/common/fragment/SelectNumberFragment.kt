package com.example.caipiao.common.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.caipiao.common.activity.BaseLotteryActivity
import com.example.caipiao.databinding.FragmentSelectNumberBinding
import com.example.caipiao.common.adapter.SelectNumberAdapter
import com.example.caipiao.common.viewmodel.BaseCommonViewModel
import com.example.caipiao.common.viewmodel.SelectNumberViewModel
import kotlinx.coroutines.launch


class SelectNumberFragment(model: BaseCommonViewModel, lottery: String) :
    BaseFragment(model, lottery) {

    private val mBinding: FragmentSelectNumberBinding by lazy {
        FragmentSelectNumberBinding.inflate(layoutInflater)
    }

    private val mSelectNumberAdapter: SelectNumberAdapter by lazy {
        SelectNumberAdapter()
    }

    private val mSelectNumberViewModel: SelectNumberViewModel by viewModels()


    override fun initRoot(): View {
        return mBinding.root
    }


    override fun initData() {
        super.initData()
        mBinding.onClickHandler = this
        mBinding.selectNumberRecycler.adapter = mSelectNumberAdapter
        lifecycleScope.launchWhenCreated {
            launch {
                mSelectNumberViewModel.mSelectNumberList.collect {
                    mSelectNumberAdapter.setData(it, lotteryType)
                }
            }
        }
        mSelectNumberAdapter.run {
            uploadList = {
                mSelectNumberViewModel.uploadNumberList(it)
            }
        }
        mSelectNumberViewModel.setLotteryType(lotteryType)
        mSelectNumberViewModel.setLotteryWeightData(
            mBaseCommonViewModel.selectBlueTotalMap,
            mBaseCommonViewModel.selectRedTotalMap,
            mBaseCommonViewModel.blueTotalWeight,
            mBaseCommonViewModel.redTotalWeight
        )
    }

    fun autoSelectNumber() {
        mSelectNumberViewModel.autoSelectNumber(5)
    }

    fun handSelectNumber() {
        mSelectNumberViewModel.autoSelectNumber(1)
    }

    fun sureSelectNumber() {
        mBaseCommonViewModel.insertHistoryData(mSelectNumberViewModel.getSelectNumberList())
        mFileFragmentListener?.onSwitchFragmentForward(BaseLotteryActivity.HISTORY_NUMBER_FRAGMENT)
        cleanRecord()
    }

    fun cleanSelectNumber() {
        cleanRecord()
    }

    private fun cleanRecord() {
        mSelectNumberViewModel.uploadNumberList(ArrayList())
    }
}