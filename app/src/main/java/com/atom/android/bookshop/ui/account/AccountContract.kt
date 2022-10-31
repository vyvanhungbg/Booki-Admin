package com.atom.android.bookshop.ui.account

import android.content.Context
import com.atom.android.bookshop.base.BasePresenter
import com.atom.android.bookshop.data.model.User
import com.atom.android.bookshop.ui.authentication.login.LoginContract

class AccountContract {
    interface View {
        fun getUserSuccess(user: User)
        fun getUserFailed(message: String?)
    }

    interface Presenter : BasePresenter<AccountContract.View> {
        fun getUser(context: Context?)
        fun destroyToken(context: Context?)
    }
}
