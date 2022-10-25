package com.atom.android.bookshop.data.source

import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

interface IForgotPasswordDataSource {
    interface Remote {
        fun requestForgotPassword(
            email: String,
            callback: IRequestCallback<ResponseObject<String>>
        )
    }
}
