package com.atom.android.bookshop.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseActivity
import com.atom.android.bookshop.data.repository.MainRepository
import com.atom.android.bookshop.data.source.remote.main.MainRemoteDataSource
import com.atom.android.bookshop.databinding.ActivityMainBinding
import com.atom.android.bookshop.ui.account.AccountFragment
import com.atom.android.bookshop.ui.authentication.AuthenticationActivity
import com.atom.android.bookshop.ui.bill.BillFragment
import com.atom.android.bookshop.ui.discount.DiscountFragment
import com.atom.android.bookshop.ui.home.HomeFragment
import com.atom.android.bookshop.utils.registerNetwork
import com.atom.android.bookshop.utils.showAlertDialogNetwork
import com.atom.android.bookshop.utils.toast

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    MainContract.View {

    private val mainPresenter by lazy {
        MainPresenter.getInstance(
            MainRepository.getInstance(
                MainRemoteDataSource.getInstance()
            ),
            this
        )
    }

    override fun initView() {
        setUpNav()
        registerNetwork(
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
            onConnectedInternet = {  },
            onLostInternet = { showAlertDialogNetwork() }
        )
    }

    override fun initData() {
        mainPresenter.checkToken(this)
    }

    override fun initEvent() {
        // TODO implement later
    }

    private fun setUpNav() {
        binding?.viewPagerMain?.adapter = ViewPagerMainAdapter(
            this@MainActivity,
            listOf<Fragment>(HomeFragment(), DiscountFragment(), BillFragment(), AccountFragment())
        )
        binding?.viewPagerMain?.isUserInputEnabled = false
        binding?.navView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    binding?.viewPagerMain?.currentItem = POSITION_FRAGMENT_HOME
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_discount -> {
                    binding?.viewPagerMain?.currentItem = POSITION_FRAGMENT_DISCOUNT
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_bill -> {
                    binding?.viewPagerMain?.currentItem = POSITION_FRAGMENT_BILL
                    return@setOnItemSelectedListener true
                }
                else -> {
                    binding?.viewPagerMain?.currentItem = POSITION_FRAGMENT_ACCOUNT
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    override fun navigate(positionFragment: Int) {
        when (positionFragment) {
            POSITION_FRAGMENT_DISCOUNT -> {
                binding?.viewPagerMain?.currentItem =
                    POSITION_FRAGMENT_DISCOUNT
            }
            POSITION_FRAGMENT_BILL -> {
                binding?.viewPagerMain?.currentItem =
                    POSITION_FRAGMENT_BILL
            }
            POSITION_FRAGMENT_ACCOUNT -> {
                binding?.viewPagerMain?.currentItem =
                    POSITION_FRAGMENT_ACCOUNT
            }
            else -> {
                binding?.viewPagerMain?.currentItem =
                    POSITION_FRAGMENT_HOME
            }
        }
    }

    override fun checkTokenSuccess(message: String?) {
        toast(R.string.text_mess_login_success)
    }

    override fun checkTokenFailed(message: String?) {
        toast(R.string.title_alert_relogin)
        navigateAuthenticationActivity()
    }

    private fun navigateAuthenticationActivity() {
        val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun setVisibleNavigationBar(isVisible: Boolean) {
        binding?.navView?.isVisible = isVisible
    }

    companion object {
        const val POSITION_FRAGMENT_HOME = 0
        const val POSITION_FRAGMENT_DISCOUNT = 1
        const val POSITION_FRAGMENT_BILL = 2
        const val POSITION_FRAGMENT_ACCOUNT = 3
    }
}
