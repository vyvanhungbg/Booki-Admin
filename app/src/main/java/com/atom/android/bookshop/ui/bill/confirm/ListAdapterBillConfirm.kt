package com.atom.android.bookshop.ui.bill.confirm

import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.databinding.ItemBillConfirmBinding
import com.atom.android.bookshop.ui.bill.DiffCallBackItemBill
import com.atom.android.bookshop.utils.Constants

class ListAdapterBillConfirm(
    private val onClick: (Bill) -> Unit,
    private val onClickConfirm: (Bill) -> Unit,
    private val onClickCancel: (Bill) -> Unit
) :
    BaseAdapter<Bill, BaseViewHolder<Bill>>(DiffCallBackItemBill()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Bill> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBillConfirmBinding.inflate(inflater, parent, false)
        val titleBill = { idBill: Int, time: String ->
            parent.context.getString(
                R.string.text_title_bill,
                idBill,
                time
            )
        }

        val contentBill = { nameOfFistBook: String, totalItem: Int ->
            parent.context.getString(
                R.string.text_content_bill,
                nameOfFistBook,
                totalItem
            )
        }

        val timeConfirm = { timeConfirm: String ->
            parent.context.getString(
                R.string.text_time_confirm,
                timeConfirm
            )
        }

        return ViewHolder(
            binding,
            titleBill,
            contentBill,
            timeConfirm,
            onClickCancel,
            onClickConfirm
        )
    }


    inner class ViewHolder(
        val binding: ItemBillConfirmBinding,
        val titleBill: (Int, String) -> String,
        val contentBill: (String, Int) -> String,
        val timeConfirm: (String) -> String,
        val onClickCancel: (Bill) -> Unit,
        val onClickConfirm: (Bill) -> Unit
    ) :
        BaseViewHolder<Bill>(binding, onClick) {
        override fun binView(item: Bill) {
            super.binView(item)
            binding.apply {
                titleBill.text = titleBill(item.id, item.createdAt)
                contentBill.text =
                    contentBill(
                        item.orderLines[Constants.FIRST_POSITION].book.title,
                        item.totalItem()
                    )
                textViewTimeConfirm.text = timeConfirm(item.getTimeConfirmed())
                textViewConfirm.setOnClickListener {
                    onClickConfirm(item)
                }
                textViewCancel.setOnClickListener {
                    onClickCancel(item)
                }
            }
        }
    }

}