package com.atom.android.bookshop.data.repository

import com.atom.android.bookshop.data.model.User
import com.atom.android.bookshop.data.source.IAccountDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

class AccountRepository(private val remote: IAccountDataSource.Remote) : IAccountDataSource.Remote {

    override fun getUser(token: String?, callback: IRequestCallback<ResponseObject<User>>) {
        remote.getUser(token, callback)
    }

    companion object {
        private var instance: AccountRepository? = null
        fun getInstance(
            remoteDataSource: IAccountDataSource.Remote
        ) = synchronized(this) {
            instance ?: AccountRepository(remoteDataSource).also { instance = it }
        }
    }
}
