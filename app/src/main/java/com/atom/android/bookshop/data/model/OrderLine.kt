package com.atom.android.bookshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderLine(
    val amount: Int,
    val book: Book,
    val price: Double
) : Parcelable {
    companion object {
        const val AMOUNT = "amount"
        const val BOOK = "book"
        const val PRICE = "price"
    }
}
