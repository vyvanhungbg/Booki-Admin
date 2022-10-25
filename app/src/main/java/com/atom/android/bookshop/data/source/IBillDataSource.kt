package com.atom.android.bookshop.data.source

import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

interface IBillDataSource {
    interface Remote {
        fun getBill(
            token: String?,
            page: Int?,
            type: Int?,
            callback: IRequestCallback<ResponseObject<List<Bill>>>
        )

        fun updateStatusBill(
            token: String?,
            idBill: Int,
            status: Int,
            reason: String?,
            callback: IRequestCallback<ResponseObject<Bill>>
        )
    }
}
