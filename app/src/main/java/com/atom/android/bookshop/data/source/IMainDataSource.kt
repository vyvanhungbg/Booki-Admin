package com.atom.android.bookshop.data.source

import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

interface IMainDataSource {
    interface Remote {
        fun checkToken(
            token: String?,
            callback: IRequestCallback<ResponseObject<String>>
        )
    }
}
