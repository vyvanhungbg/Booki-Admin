package com.atom.android.bookshop.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
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
    fun totalBill() = totalPriceOfItems() + shippingMethod.cost

    fun totalPriceOfItems() = orderLines.sumOf { it.amount * it.price }

    fun totalItem() = orderLines.sumOf { it.amount }

    fun getTimeOrder() =
        orderHistories.find { orderHistory -> orderHistory.status.id == ApiConstants.TYPEOFBILL.PENDING }?.statusDate
            ?: Constants.DEFAULT_STRING

    fun getTimeConfirmed() =
        orderHistories.find { orderHistory -> orderHistory.status.id == ApiConstants.TYPEOFBILL.ACCEPT }?.statusDate
            ?: Constants.DEFAULT_STRING

    fun getTimeDelivery() =
        orderHistories.find { orderHistory -> orderHistory.status.id == ApiConstants.TYPEOFBILL.DELIVERY }?.statusDate
            ?: Constants.DEFAULT_STRING

    fun getTimeDone() =
        orderHistories.find { orderHistory -> orderHistory.status.id == ApiConstants.TYPEOFBILL.SUCCESS }?.statusDate
            ?: Constants.DEFAULT_STRING

    class DiffCallBackItemBill : DiffUtil.ItemCallback<Bill>() {
        override fun areItemsTheSame(oldItemSearch: Bill, newItemSearch: Bill): Boolean {
            return oldItemSearch.id == newItemSearch.id
        }

        override fun areContentsTheSame(oldItemSearch: Bill, newItemSearch: Bill): Boolean {
            return oldItemSearch == newItemSearch
        }
    }

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
        const val ACTION_ITEM = 0
        const val ACTION_CONFIRM = 1
        const val ACTION_CANCEL = 2
    }
}
