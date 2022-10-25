package com.atom.android.bookshop.data.repository

import com.atom.android.bookshop.data.source.IMainDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

class MainRepository(private val remote: IMainDataSource.Remote) : IMainDataSource.Remote {
    override fun checkToken(token: String?, callback: IRequestCallback<ResponseObject<String>>) {
        remote.checkToken(token, callback)
    }

    companion object {
        private var instance: MainRepository? = null
        fun getInstance(
            remoteDataSource: IMainDataSource.Remote
        ) = synchronized(this) {
            instance ?: MainRepository(remoteDataSource).also { instance = it }
        }
    }
}
