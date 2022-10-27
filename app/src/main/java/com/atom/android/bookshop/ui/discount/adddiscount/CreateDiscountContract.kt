package com.atom.android.bookshop.ui.discount.adddiscount

import android.content.Context
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity

class CreateDiscountContract {
    interface View {
        fun createDiscountSuccess(discount: Discount)
        fun createDiscountFailed(message: String?)
        fun validateDiscountSuccess(discount: DiscountEntity)
        fun validateDiscountFailed(message: String?)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {
        fun createDiscount(context: Context?, discount: DiscountEntity)
        fun validateDiscount(
            context: Context?,
            discount: DiscountEntity
        )
    }
}
