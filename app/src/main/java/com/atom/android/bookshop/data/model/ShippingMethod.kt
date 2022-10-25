package com.atom.android.bookshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShippingMethod(
    val id: Int,
    val name: String,
    val cost: Double,
    val distanceAbove: Double,
) : Parcelable {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val COST = "cost"
        const val DISTANCE_ABOVE = "distanceAbove"
    }
}
