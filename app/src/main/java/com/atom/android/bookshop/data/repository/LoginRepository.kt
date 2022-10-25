package com.atom.android.bookshop.data.repository

import com.atom.android.bookshop.data.source.ILoginDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

class LoginRepository(
    private val local: ILoginDataSource.Local,
    private val remote: ILoginDataSource.Remote
) : ILoginDataSource.Local, ILoginDataSource.Remote {

    override fun login(
        email: String,
        password: String,
        callback: IRequestCallback<ResponseObject<String>>
    ) {
        remote.login(email, password, callback)
    }

    override fun checkLogin(callback: IRequestCallback<ResponseObject<String>>) {
        local.checkLogin(callback)
    }

    companion object {
        private var instance: LoginRepository? = null
        fun getInstance(
            localDataSource: ILoginDataSource.Local,
            remoteDataSource: ILoginDataSource.Remote
        ) = synchronized(this) {
            instance ?: LoginRepository(localDataSource, remoteDataSource).also { instance = it }
        }
    }
}
