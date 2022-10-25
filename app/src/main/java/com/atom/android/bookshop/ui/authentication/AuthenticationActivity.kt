package com.atom.android.bookshop.ui.authentication

import androidx.fragment.app.Fragment
import com.atom.android.bookshop.base.BaseActivity
import com.atom.android.bookshop.databinding.ActivityAuthenticationBinding
import com.atom.android.bookshop.ui.authentication.forgotpassword.ForgotPasswordFragment
import com.atom.android.bookshop.ui.authentication.login.LoginFragment
import com.atom.android.bookshop.ui.main.ViewPagerMainAdapter

class AuthenticationActivity :
    BaseActivity<ActivityAuthenticationBinding>(ActivityAuthenticationBinding::inflate) {

    override fun initView() {
        binding?.viewPagerAuthentication?.adapter = ViewPagerMainAdapter(
            this@AuthenticationActivity,
            listOf<Fragment>(LoginFragment(), ForgotPasswordFragment())
        )
    }

    override fun initData() {
        // late init
    }

    override fun initEvent() {
        // late init
    }

    override fun navigate(positionFragment: Int) {
        when (positionFragment) {
            POSITION_FRAGMENT_FORGOT_PASSWORD -> {
                binding?.viewPagerAuthentication?.currentItem = POSITION_FRAGMENT_FORGOT_PASSWORD
            }
            else -> {
                binding?.viewPagerAuthentication?.currentItem = POSITION_FRAGMENT_LOGIN
            }
        }
    }

    companion object {
        const val POSITION_FRAGMENT_LOGIN = 0
        const val POSITION_FRAGMENT_FORGOT_PASSWORD = 1
    }
}
