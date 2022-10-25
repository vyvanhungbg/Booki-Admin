package com.atom.android.bookshop.data.source

import com.atom.android.bookshop.data.model.User
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

interface IAccountDataSource {
    interface Remote {
        fun getUser(
            token: String?,
            callback: IRequestCallback<ResponseObject<User>>
        )
    }
}
