package com.atom.android.bookshop.ui.bill.delivery

import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.databinding.ItemBillDeliveryBinding
import com.atom.android.bookshop.utils.Constants

class ListAdapterBillDelivery(
    private val onClick: (Bill, Int) -> Unit,
) : BaseAdapter<Bill, BaseViewHolder<Bill>>(Bill.DiffCallBackItemBill()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Bill> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBillDeliveryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemBillDeliveryBinding) :
        BaseViewHolder<Bill>(binding) {
        override fun binView(item: Bill) {
            super.binView(item)
            binding.apply {
                val context = binding.root.context
                textViewTitleBill.text = binding.root.context.getString(
                    R.string.text_title_bill,
                    item.id, item.createdAt
                )
                textViewContentBill.text = context.getString(
                    R.string.text_content_bill,
                    item.orderLines[Constants.FIRST_POSITION].book.title,
                    item.totalItem()
                )
                textViewTimeConfirm.text = context.getString(
                    R.string.text_time_confirm,
                    item.getTimeConfirmed()
                )
                textViewTimeDelivery.text = context.getString(
                    R.string.text_time_delivery,
                    item.getTimeDelivery()
                )
                textViewConfirm.setOnClickListener {
                    onClick(item, Bill.ACTION_CONFIRM)
                }
                textViewCancel.setOnClickListener {
                    onClick(item, Bill.ACTION_CANCEL)
                }
                itemView.setOnClickListener {
                    onClick(item, Bill.ACTION_ITEM)
                }
            }
        }
    }
}
