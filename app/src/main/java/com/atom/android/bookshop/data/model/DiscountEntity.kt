package com.atom.android.bookshop.data.model

data class DiscountEntity(
    val name: String,
    val value: Double,
    val code: String,
    val amount: Int,
    val image: String,
    val timeEnd: String?,
    val timeStart: String?,
    val isVisible: Int,
    val idBook: List<Int>?
) {
    companion object {
        const val NAME = "name"
        const val VALUE = "value"
        const val CODE = "code"
        const val AMOUNT = "amount"
        const val IMAGE = "image"
        const val TIME_END = "timeEnd"
        const val TIME_START = "timeStart"
        const val IS_VISIBLE = "isVisible"
        const val ID_BOOK = "idBook"
    }
}
