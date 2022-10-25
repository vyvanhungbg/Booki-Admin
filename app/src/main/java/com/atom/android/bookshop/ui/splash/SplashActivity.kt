package com.atom.android.bookshop.ui.splash

import android.content.Intent
import com.atom.android.bookshop.base.BaseActivity
import com.atom.android.bookshop.databinding.ActivitySplashBinding
import com.atom.android.bookshop.ui.main.MainActivity
import java.util.Timer
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun initView() {
        Timer().schedule(DELAY_TIME) {
            this@SplashActivity.runOnUiThread {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun initData() {
        // TODO implement later
    }

    override fun initEvent() {
        // TODO implement later
    }

    companion object {
        private const val DELAY_TIME = 1000L
    }

    override fun navigate(position: Int) {
        // TODO implement later
    }
}
