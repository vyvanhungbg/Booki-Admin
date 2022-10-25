package com.atom.android.bookshop.ui.account

import android.content.Context
import com.atom.android.bookshop.data.model.User
import com.atom.android.bookshop.data.repository.AccountRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.destroyToken
import com.atom.android.bookshop.utils.getTokenLogin


class AccountPresenter(
    private val repository: AccountRepository,
    private val accountView: AccountContract.View
) : AccountContract.Presenter {

    override fun getUser(context: Context?) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        repository.getUser(token, object : IRequestCallback<ResponseObject<User>> {
            override fun onSuccess(responseObject: ResponseObject<User>) {
                accountView.getUserSuccess(responseObject.data as User)
            }
            override fun onFailed(message: String?) {
                accountView.getUserFailed(message)
            }

        })
    }

    override fun destroyToken(context: Context?) {
        SharedPreferenceUtils.getInstance(context)?.destroyToken()
    }

    override fun onStart() {
        // TODO implement later
    }

    override fun onStop() {
        // TODO implement later
    }

    companion object {
        private var instance: AccountPresenter? = null
        fun getInstance(
            repository: AccountRepository,
            accountView: AccountContract.View
        ) = synchronized(this) {
            instance ?: AccountPresenter(repository, accountView).also {
                instance = it
            }
        }
    }
}
