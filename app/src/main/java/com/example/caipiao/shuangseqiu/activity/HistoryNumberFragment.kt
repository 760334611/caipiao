package com.example.caipiao.shuangseqiu.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.caipiao.common.FileFragmentListener
import com.example.caipiao.databinding.FragmentHistoryNumberBinding
import com.example.caipiao.shuangseqiu.adapter.HistoryTimeAdapter
import com.example.caipiao.shuangseqiu.viewmodel.MainShuangSeQiuViewModel
import kotlinx.coroutines.launch


class HistoryNumberFragment : Fragment() {

    var mFileFragmentListener: FileFragmentListener? = null

    private val mBinding: FragmentHistoryNumberBinding by lazy {
        FragmentHistoryNumberBinding.inflate(layoutInflater)
    }

    private val mHistoryTimeAdapter: HistoryTimeAdapter by lazy {
        HistoryTimeAdapter()
    }

    private val mMainShuangSeQiuViewModel: MainShuangSeQiuViewModel by activityViewModels()

    companion object {
        @JvmStatic
        fun newInstance(): HistoryNumberFragment {
            return HistoryNumberFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        mBinding.recycleTime.adapter = mHistoryTimeAdapter
        mHistoryTimeAdapter.run {
            itemClick = {
                mFileFragmentListener?.onSwitchFragmentLottery(it.selectNumberList,it.prizeDesignatedTime)
            }
        }
        lifecycleScope.launchWhenCreated {
            launch {
                mMainShuangSeQiuViewModel.mHistoryTimeList.collect {
                    mHistoryTimeAdapter.setData(it)
                }
            }
        }
        mHistoryTimeAdapter.setData(mMainShuangSeQiuViewModel.getHistorySelectList())
    }
}