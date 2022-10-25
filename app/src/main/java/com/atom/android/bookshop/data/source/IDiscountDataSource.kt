package com.atom.android.bookshop.data.source

import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject

interface IDiscountDataSource {
    interface Remote {
        fun getDiscount(
            token: String?,
            page: Int?,
            type: Int?,
            callback: IRequestCallback<ResponseObject<List<Discount>>>
        )

        fun createDiscount(
            token: String?,
            discount: DiscountEntity,
            callback: IRequestCallback<ResponseObject<Discount>>
        )
    }
}
