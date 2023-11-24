package com.example.caipiao.shuangseqiu.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.caipiao.common.FileFragmentListener
import com.example.caipiao.databinding.FragmentSelectNumberBinding
import com.example.caipiao.shuangseqiu.adapter.SelectNumberAdapter
import com.example.caipiao.shuangseqiu.bean.SelectNumber
import com.example.caipiao.shuangseqiu.viewmodel.MainShuangSeQiuViewModel
import com.example.caipiao.shuangseqiu.viewmodel.SelectNumberViewModel
import kotlinx.coroutines.launch


class SelectNumberFragment : Fragment() {

    var mFileFragmentListener: FileFragmentListener? = null

    private val mBinding: FragmentSelectNumberBinding by lazy {
        FragmentSelectNumberBinding.inflate(layoutInflater)
    }

    private val mSelectNumberAdapter: SelectNumberAdapter by lazy{
        SelectNumberAdapter()
    }

    private val mSelectNumberViewModel: SelectNumberViewModel by viewModels()

    private val mMainShuangSeQiuViewModel: MainShuangSeQiuViewModel by activityViewModels()

    companion object {
        @JvmStatic
        fun newInstance(): SelectNumberFragment {
            return SelectNumberFragment()
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
        mBinding.onClickHandler = this
        mBinding.selectNumberRecycler.adapter=mSelectNumberAdapter
        lifecycleScope.launchWhenCreated {
            launch {
                mSelectNumberViewModel.mSelectNumberList.collect{
                    mSelectNumberAdapter.setData(it)
                    mMainShuangSeQiuViewModel.uploadClickPosition(it)
                }
            }
        }
        mSelectNumberAdapter.run {
            uploadList={
                mSelectNumberViewModel.uploadNumberList(it)
                mMainShuangSeQiuViewModel.uploadClickPosition(it)
            }
        }
    }

    fun autoSelectNumber(){
        mSelectNumberViewModel.autoSelectNumber(5)
    }

    fun handSelectNumber(){
        mSelectNumberViewModel.autoSelectNumber(1)
    }

    fun setHistoryRecord(list:ArrayList<SelectNumber>){
        Log.i("zhanghao","setHistoryRecord size=${list.size}")
        mSelectNumberViewModel.uploadNumberList(list)
    }
}