package com.example.caipiao.common.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.lifecycleScope
import com.example.caipiao.R
import com.example.caipiao.common.FileFragmentListener
import com.example.caipiao.common.fragment.HistoryNumberFragment
import com.example.caipiao.common.fragment.LotteryResultsFragment
import com.example.caipiao.common.fragment.SelectNumberFragment
import com.example.caipiao.common.adapter.HistoryPrizeAdapter
import com.example.caipiao.common.bean.SelectNumber
import com.example.caipiao.common.viewmodel.BaseCommonViewModel
import com.example.caipiao.databinding.BaseLotteryActivityBinding
import kotlinx.coroutines.launch

open class BaseLotteryActivity(model: BaseCommonViewModel, lotteryType: String) :
    AppCompatActivity() {

    open val mBinding: BaseLotteryActivityBinding by lazy {
        BaseLotteryActivityBinding.inflate(layoutInflater)
    }

    open val mHistoryPrizeAdapter: HistoryPrizeAdapter by lazy {
        HistoryPrizeAdapter()
    }

    open val mSelectNumberFragment: SelectNumberFragment by lazy {
        SelectNumberFragment(model, lotteryType).apply {
            mFileFragmentListener = fragmentListener
        }
    }
    open val mHistoryNumberFragment: HistoryNumberFragment by lazy {
        HistoryNumberFragment(model, lotteryType).apply {
            mFileFragmentListener = fragmentListener
        }
    }

    open val mLotteryResultsFragment: LotteryResultsFragment by lazy {
        LotteryResultsFragment(model, lotteryType).apply {
            mFileFragmentListener = fragmentListener
        }
    }

    open val mMainDaLeTouViewModel: BaseCommonViewModel by lazy {
        ViewModelProvider(this).get(model::class.java)
    }

    open var fragmentListener: FileFragmentListener = object : FileFragmentListener() {
        override fun onSwitchFragmentBack(tag: String) {

        }

        override fun onSwitchFragmentForward(tag: String) {
            showFragment(tag)
        }

        override fun onSwitchFragmentLottery(list: List<SelectNumber>, prizeDesignatedTime: Long) {
            showFragment(LOTTERY_RESULTS_FRAGMENT)
            mLotteryResultsFragment.setSelectNumberList(list, prizeDesignatedTime)
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


    open fun initData() {
        mBinding.onClickHandler = this
        mBinding.recycleHistoryNumber.adapter = mHistoryPrizeAdapter
        lifecycleScope.launchWhenCreated {
            launch {
                mMainDaLeTouViewModel.mHistoryPrizeList.collect {
                    mHistoryPrizeAdapter.setData(it)
                }
            }
        }

        mMainDaLeTouViewModel.getPrizeDataDB()
        mMainDaLeTouViewModel.getHistoryData()
    }

    fun checkHistory() {
        showFragment(HISTORY_NUMBER_FRAGMENT)
    }

    fun selectNumber() {
        showFragment(SELECT_NUMBER_FRAGMENT)
    }

    fun refreshNumber() {
        mMainDaLeTouViewModel.getHistoryPrizeData()
    }


    open fun showFragment(tag: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(tag)
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

    open fun hideFragment() {
        mBinding.fmOther.visibility = View.GONE
        mBinding.group.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (mLotteryResultsFragment.isVisible) {
            showFragment(HISTORY_NUMBER_FRAGMENT)
        } else if (mBinding.group.visibility == View.VISIBLE) {
            finish()
        } else {
            hideFragment()
        }
    }
}

