package com.atom.android.bookshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Discount(
    val id: Int,
    val name: String,
    val value: Double,
    val code: String,
    val amount: Int,
    val image: String,
    val createdAt: String,
    val timeEnd: String,
    val timeStart: String,
    val isVisible: Int,
    val enabled: Int,
    val bookDiscounts: List<Book>
) : Parcelable {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val VALUE = "value"
        const val CODE = "code"
        const val AMOUNT = "amount"
        const val IMAGE = "image"
        const val CREATED_AT = "createdAt"
        const val TIME_END = "timeEnd"
        const val TIME_START = "timeStart"
        const val IS_VISIBLE = "isVisible"
        const val ENABLED = "enabled"
        const val BOOK_DISCOUNTS = "bookDiscounts"
    }
}
