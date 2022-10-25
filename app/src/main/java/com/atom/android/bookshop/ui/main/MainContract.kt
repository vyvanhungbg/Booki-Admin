package com.atom.android.bookshop.ui.main

import android.content.Context
import com.atom.android.bookshop.base.BasePresenter
import com.atom.android.bookshop.ui.authentication.login.LoginContract

class MainContract {
    interface View {
        fun checkTokenSuccess(message: String?)
        fun checkTokenFailed(message: String?)
    }

    interface Presenter : BasePresenter<LoginContract.View> {
        fun checkToken(context: Context?)
    }
}
