package com.atom.android.bookshop.ui.bill.pending

import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.databinding.ItemBillBinding
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.convertStrToMoney

class ListAdapterBillPending(
    private val onClick: (Bill, action: Int) -> Unit
) : BaseAdapter<Bill, BaseViewHolder<Bill>>(Bill.DiffCallBackItemBill()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Bill> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBillBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemBillBinding) : BaseViewHolder<Bill>(binding) {
        override fun binView(item: Bill) {
            super.binView(item)
            binding.apply {
                val context = binding.root.context
                textViewTitleBill.text = context.getString(
                    R.string.text_title_bill,
                    item.id, item.createdAt
                )
                contentBill.text = context.getString(
                    R.string.text_content_bill,
                    item.orderLines[Constants.FIRST_POSITION].book.title,
                    item.totalItem()
                )
                textViewTitleTotalMoney.text = item.totalBill().toString().convertStrToMoney()
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
