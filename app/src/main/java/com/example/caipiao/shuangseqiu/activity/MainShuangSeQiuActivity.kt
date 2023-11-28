package com.example.caipiao.shuangseqiu.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.caipiao.R
import com.example.caipiao.common.FileFragmentListener
import com.example.caipiao.databinding.MainShuangSeQiuActivityBinding
import com.example.caipiao.shuangseqiu.adapter.HistoryPrizeAdapter
import com.example.caipiao.shuangseqiu.bean.SelectNumber
import com.example.caipiao.shuangseqiu.viewmodel.MainShuangSeQiuViewModel
import kotlinx.coroutines.launch

class MainShuangSeQiuActivity : AppCompatActivity() {

    private val mBinding: MainShuangSeQiuActivityBinding by lazy {
        MainShuangSeQiuActivityBinding.inflate(layoutInflater)
    }

    private val mHistoryPrizeAdapter: HistoryPrizeAdapter by lazy {
        HistoryPrizeAdapter()
    }

    private val mSelectNumberFragment: SelectNumberFragment by lazy {
        SelectNumberFragment.newInstance().apply {
            mFileFragmentListener = fragmentListener
        }
    }
    private val mHistoryNumberFragment: HistoryNumberFragment by lazy {
        HistoryNumberFragment.newInstance().apply {
            mFileFragmentListener = fragmentListener
        }
    }

    private val mLotteryResultsFragment: LotteryResultsFragment by lazy {
        LotteryResultsFragment.newInstance().apply {
            mFileFragmentListener = fragmentListener
        }
    }


    private val mMainShuangSeQiuViewModel: MainShuangSeQiuViewModel by viewModels()

    private var fragmentListener: FileFragmentListener = object : FileFragmentListener() {
        override fun onSwitchFragmentBack(tag: String) {

        }

        override fun onSwitchFragmentForward(tag: String) {
            showFragment(tag)
        }

        override fun onSwitchFragmentLottery(list: List<SelectNumber>, prizeDesignatedTime: Long) {
            showFragment(LOTTERY_RESULTS_FRAGMENT)
            mLotteryResultsFragment.setSelectNumberList(list,prizeDesignatedTime)
        }

        override fun onSwitchFragmentRetreat() {
            hideFragment()
        }

        override fun onActivityFinish() {
            finish()
        }
    }

    companion object {
        const val SELECT_NUMBER_FRAGMENT = "SelectNumberFragment"
        const val HISTORY_NUMBER_FRAGMENT = "HistoryNumberFragment"
        const val LOTTERY_RESULTS_FRAGMENT = "LotteryResultsFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initData()
    }


    private fun initData() {
        mBinding.onClickHandler = this
        mBinding.recycleHistoryNumber.adapter = mHistoryPrizeAdapter
        lifecycleScope.launchWhenCreated {
            launch {
                mMainShuangSeQiuViewModel.mHistoryPrizeList.collect {
                    mHistoryPrizeAdapter.setData(it)
                }
            }
        }

        mMainShuangSeQiuViewModel.getPrizeDataDB()
        mMainShuangSeQiuViewModel.getHistoryData()
    }

    fun checkHistory() {
        showFragment(HISTORY_NUMBER_FRAGMENT)
    }

    fun selectNumber() {
        showFragment(SELECT_NUMBER_FRAGMENT)
    }

    fun refreshNumber() {
        mMainShuangSeQiuViewModel.getHistoryPrizeData()
    }


    private fun showFragment(tag: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment= supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = when (tag) {
                SELECT_NUMBER_FRAGMENT -> {
                    mSelectNumberFragment
                }
                HISTORY_NUMBER_FRAGMENT -> {
                    mHistoryNumberFragment
                }
                LOTTERY_RESULTS_FRAGMENT -> {
                    mLotteryResultsFragment
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
        mBinding.fmOther.visibility = View.VISIBLE
        mBinding.group.visibility = View.GONE
    }

    private fun hideFragment() {
        mBinding.fmOther.visibility = View.GONE
        mBinding.group.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if(mLotteryResultsFragment.isVisible){
            showFragment(HISTORY_NUMBER_FRAGMENT)
        }else if (mBinding.group.visibility == View.VISIBLE) {
            finish()
        } else {
            hideFragment()
        }
    }
}