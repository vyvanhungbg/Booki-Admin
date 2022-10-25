package com.atom.android.bookshop.data.repository

import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity
import com.atom.android.bookshop.data.source.IDiscountDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

class DiscountRepository(private val remote: IDiscountDataSource.Remote) :
    IDiscountDataSource.Remote {

    override fun getDiscount(
        token: String?,
        page: Int?,
        type: Int?,
        callback: IRequestCallback<ResponseObject<List<Discount>>>
    ) {
        remote.getDiscount(token, page, type, callback)
    }

    override fun createDiscount(
        token: String?,
        discount: DiscountEntity,
        callback: IRequestCallback<ResponseObject<Discount>>
    ) {
        remote.createDiscount(token, discount, callback)
    }

    companion object {
        private var instance: DiscountRepository? = null
        fun getInstance(
            remoteDataSource: IDiscountDataSource.Remote
        ) = synchronized(this) {
            instance ?: DiscountRepository(remoteDataSource).also { instance = it }
        }
    }
}
