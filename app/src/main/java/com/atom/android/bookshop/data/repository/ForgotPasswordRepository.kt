package com.atom.android.bookshop.data.repository

import com.atom.android.bookshop.data.source.IForgotPasswordDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

class ForgotPasswordRepository(private val remote: IForgotPasswordDataSource.Remote) :
    IForgotPasswordDataSource.Remote {

    override fun requestForgotPassword(
        email: String,
        callback: IRequestCallback<ResponseObject<String>>
    ) {
        remote.requestForgotPassword(email, callback)
    }

    companion object {
        private var instance: ForgotPasswordRepository? = null
        fun getInstance(
            remoteDataSource: IForgotPasswordDataSource.Remote
        ) = synchronized(this) {
            instance ?: ForgotPasswordRepository(remoteDataSource).also { instance = it }
        }
    }
}
