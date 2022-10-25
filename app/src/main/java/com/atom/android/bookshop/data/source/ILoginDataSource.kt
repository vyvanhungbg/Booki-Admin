package com.atom.android.bookshop.data.source

import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

interface ILoginDataSource {
    interface Remote {
        fun login(
            email: String,
            password: String,
            callback: IRequestCallback<ResponseObject<String>>
        )
    }

    interface Local {
        fun checkLogin(callback: IRequestCallback<ResponseObject<String>>)
    }
}
