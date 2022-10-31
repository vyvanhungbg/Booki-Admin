package com.atom.android.bookshop.ui.bill.success

import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.databinding.ItemBillSuccessBinding
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.convertStrToMoney

class ListAdapterBillSuccess(
    private val onClick: (Bill) -> Unit
) : BaseAdapter<Bill, BaseViewHolder<Bill>>(Bill.DiffCallBackItemBill()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Bill> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBillSuccessBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(
        val binding: ItemBillSuccessBinding,
    ) :
        BaseViewHolder<Bill>(binding) {
        override fun binView(item: Bill) {
            super.binView(item)
            binding.apply {
                val context = root.context
                textViewTitleBill.text = context.getString(
                    R.string.text_title_bill,
                    item.id, item.createdAt
                )
                textViewContentBill.text =
                    context.getString(
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
                textViewTimeDone.text = context.getString(
                    R.string.text_time_done,
                    item.getTimeDone()
                )
                textViewTitleTotalMoney.text = item.totalBill().toString().convertStrToMoney()
                textViewDetail.setOnClickListener {
                    onClick(item)
                }
                itemView.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}
