package com.example.caipiao.shuangseqiu.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.caipiao.R
import com.example.caipiao.common.FileFragmentListener
import com.example.caipiao.databinding.MainShuangSeQiuActivityBinding
import com.example.caipiao.shuangseqiu.adapter.HistoryTimeAdapter
import com.example.caipiao.shuangseqiu.viewmodel.MainShuangSeQiuViewModel
import kotlinx.coroutines.launch

class MainShuangSeQiuActivity : AppCompatActivity() {

    private val mBinding: MainShuangSeQiuActivityBinding by lazy {
        MainShuangSeQiuActivityBinding.inflate(layoutInflater)
    }

    private val mHistoryTimeAdapter: HistoryTimeAdapter by lazy{
        HistoryTimeAdapter()
    }

    private val mSelectNumberFragment: SelectNumberFragment by lazy {
        SelectNumberFragment.newInstance().apply {
            mFileFragmentListener = fragmentListener
        }
    }


    private val mMainShuangSeQiuViewModel: MainShuangSeQiuViewModel by viewModels()

    private var fragmentListener: FileFragmentListener = object : FileFragmentListener() {
        override fun onSwitchFragmentBack(tag: String) {

        }

        override fun onSwitchFragmentForward(tag: String) {

        }

        override fun onSwitchFragmentForward(tagFrom: String, tagTo: String) {

        }

        override fun onActivityFinish() {
            finish()
        }
    }

    companion object {
        const val SELECT_NUMBER_FRAGMENT = "SelectNumberFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(mBinding.root)
        initData()
    }

    private fun initData() {
        mBinding.onClickHandler = this
        mBinding.recycleTime.adapter=mHistoryTimeAdapter
        lifecycleScope.launchWhenCreated {
            launch {
                mMainShuangSeQiuViewModel.mHistoryTimeList.collect{
                    mHistoryTimeAdapter.setData(it)
                }
            }
        }
        mMainShuangSeQiuViewModel.getHistoryData()
    }

    fun checkHistory() {

    }

    fun selectNumber() {
        showFragment(SELECT_NUMBER_FRAGMENT)
    }


    private fun showFragment(tag: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = when (tag) {
                SELECT_NUMBER_FRAGMENT -> {
                    mSelectNumberFragment
                }
                else -> {
                    mSelectNumberFragment
                }
            }
            transaction.add(R.id.fm_other, fragment, tag)

        }
        supportFragmentManager.fragments.filter { it != fragment }.forEach { transaction.hide(it) }
        transaction.show(fragment)
        transaction.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
        mBinding.fmOther.visibility= View.VISIBLE
    }

    fun hideFragment(){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.fragments.forEach { transaction.hide(it) }
        transaction.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }
}