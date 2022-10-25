package com.atom.android.bookshop.data.model

import android.os.Parcelable
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bill(
    val id: Int,
    val shippingMethod: ShippingMethod,
    val address: String,
    val note: String,
    val phone: String,
    val receiver: String,
    val orderHistories: List<OrderHistory>,
    val orderLines: List<OrderLine>,
    val createdAt: String,
) : Parcelable {
    fun totalBill(): Double = totalPriceOfItems() + shippingMethod.cost

    fun totalPriceOfItems(): Double = orderLines.sumOf { it.amount * it.price }

    fun totalItem(): Int = orderLines.sumOf { it.amount }

    fun getTimeOrder(): String =
        orderHistories.find { orderHistory -> orderHistory.status.id == ApiConstants.TYPEOFBILL.PENDING }?.statusDate
            ?: Constants.DEFAULT_STRING

    fun getTimeConfirmed(): String =
        orderHistories.find { orderHistory -> orderHistory.status.id == ApiConstants.TYPEOFBILL.ACCEPT }?.statusDate
            ?: Constants.DEFAULT_STRING

    fun getTimeDelivery(): String =
        orderHistories.find { orderHistory -> orderHistory.status.id == ApiConstants.TYPEOFBILL.DELIVERY }?.statusDate
            ?: Constants.DEFAULT_STRING

    fun getTimeDone(): String =
        orderHistories.find { orderHistory -> orderHistory.status.id == ApiConstants.TYPEOFBILL.SUCCESS }?.statusDate
            ?: Constants.DEFAULT_STRING

    companion object {
        const val ID = "id"
        const val SHIPPING_METHOD = "shippingMethod"
        const val ADDRESS = "address"
        const val NOTE = "note"
        const val PHONE = "phone"
        const val RECEIVER = "receiver"
        const val ORDER_HISTORIES = "orderHistories"
        const val ORDER_LINES = "orderLines"
        const val CREATED_AT = "createdAt"
    }
}
