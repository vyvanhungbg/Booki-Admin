package com.atom.android.bookshop.ui.discount

import android.content.Context
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity

class DiscountContract {
    interface View {
        fun getDiscountSuccess(discount: List<Discount>)
        fun getDiscountFailed(message: String?)
        fun createDiscountSuccess(discount: Discount)
        fun createDiscountFailed(message: String?)
    }

    interface Presenter {
        fun getDiscount(context: Context?, currentPage: Int, type: Int)
        fun createDiscount(context: Context?, discount: DiscountEntity)
    }
}