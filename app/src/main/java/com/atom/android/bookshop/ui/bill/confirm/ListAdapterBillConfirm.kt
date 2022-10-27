package com.atom.android.bookshop.ui.bill.confirm

import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.databinding.ItemBillConfirmBinding
import com.atom.android.bookshop.utils.Constants

class ListAdapterBillConfirm(
    private val onClick: (Bill, action: Int) -> Unit
) : BaseAdapter<Bill, BaseViewHolder<Bill>>(Bill.DiffCallBackItemBill()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Bill> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBillConfirmBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemBillConfirmBinding) : BaseViewHolder<Bill>(binding) {
        override fun binView(item: Bill) {
            super.binView(item)
            binding.apply {
                titleBill.text = binding.root.context.getString(
                    R.string.text_title_bill,
                    item.id, item.createdAt
                )
                contentBill.text = binding.root.context.getString(
                    R.string.text_content_bill,
                    item.orderLines[Constants.FIRST_POSITION].book.title,
                    item.totalItem()
                )
                textViewTimeConfirm.text = binding.root.context.getString(
                    R.string.text_time_confirm,
                    item.getTimeConfirmed()
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
