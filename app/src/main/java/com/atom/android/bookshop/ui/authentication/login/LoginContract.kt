package com.atom.android.bookshop.ui.authentication.login

import android.content.Context
import com.atom.android.bookshop.base.BasePresenter
import com.atom.android.bookshop.data.model.LoginEntity

class LoginContract {
    interface View {
        fun loginSuccess(token: String?)
        fun loginFailed(message: String?)
    }

    interface Presenter : BasePresenter<LoginContract.View> {
        fun login(context: Context?, email: String, password: String)
        fun saveToken(context: Context?, token: String?)
    }
}
