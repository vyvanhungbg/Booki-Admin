package com.atom.android.bookshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    val id: Int,
    val statusValue: String
): Parcelable {
    companion object {
        const val ID = "id"
        const val STATUS_VALUE = "statusValue"
    }
}
