package com.atom.android.bookshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderHistory(
    val reason: String,
    val status: Status,
    val statusDate: String
) : Parcelable {
    companion object {
        const val REASON = "reason"
        const val STATUS = "status"
        const val STATUS_DATE = "statusDate"
    }
}
