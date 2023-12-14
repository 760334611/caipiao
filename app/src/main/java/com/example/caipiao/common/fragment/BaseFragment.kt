package com.example.caipiao.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.caipiao.common.FileFragmentListener
import com.example.caipiao.common.viewmodel.BaseCommonViewModel


open class BaseFragment(model: BaseCommonViewModel, lottery: String) : Fragment() {

    var mFileFragmentListener: FileFragmentListener? = null


    open val lotteryType: String by lazy {
        lottery
    }

    open val mBaseCommonViewModel: BaseCommonViewModel by lazy {
        ViewModelProvider(requireActivity()).get(model::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initRoot()
    }

    open fun initRoot(): View? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    open fun initData() {

    }

    open fun initView() {

    }
}