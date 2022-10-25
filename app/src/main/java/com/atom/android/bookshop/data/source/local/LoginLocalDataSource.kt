package com.atom.android.bookshop.data.source.local

import com.atom.android.bookshop.data.source.ILoginDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

class LoginLocalDataSource : ILoginDataSource.Local {

    override fun checkLogin(callback: IRequestCallback<ResponseObject<String>>) {
        // TODO implement later
    }

    companion object {
        private var instance: LoginLocalDataSource? = null
        fun getInstance() = synchronized(this) {
            instance ?: LoginLocalDataSource().also { instance = it }
        }
    }
}
