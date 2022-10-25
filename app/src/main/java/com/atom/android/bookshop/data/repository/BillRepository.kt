package com.atom.android.bookshop.data.repository

import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.source.IBillDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

class BillRepository(private val remote: IBillDataSource.Remote) : IBillDataSource.Remote {

    override fun getBill(
        token: String?,
        page: Int?,
        type: Int?,
        callback: IRequestCallback<ResponseObject<List<Bill>>>
    ) {
        remote.getBill(token, page, type, callback)
    }

    override fun updateStatusBill(
        token: String?,
        idBill: Int,
        status: Int,
        reason: String?,
        callback: IRequestCallback<ResponseObject<Bill>>
    ) {
        remote.updateStatusBill(token, idBill, status, reason, callback)
    }

    companion object {
        private var instance: BillRepository? = null
        fun getInstance(
            remoteDataSource: IBillDataSource.Remote
        ) = synchronized(this) {
            instance ?: BillRepository(remoteDataSource).also { instance = it }
        }
    }
}
