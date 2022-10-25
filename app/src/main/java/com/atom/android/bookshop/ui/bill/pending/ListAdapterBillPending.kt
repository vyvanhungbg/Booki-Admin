package com.atom.android.bookshop.ui.bill.pending

import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.databinding.ItemBillBinding
import com.atom.android.bookshop.ui.bill.DiffCallBackItemBill
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.convertStrToMoney

class ListAdapterBillPending(
    private val onClick: (Bill) -> Unit,
    private val onClickConfirm: (Bill) -> Unit,
    private val onClickCancel: (Bill) -> Unit
) :
    BaseAdapter<Bill, BaseViewHolder<Bill>>(DiffCallBackItemBill()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Bill> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBillBinding.inflate(inflater, parent, false)
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

        return ViewHolder(binding, titleBill, contentBill, onClickCancel, onClickConfirm)
    }


    inner class ViewHolder(
        val binding: ItemBillBinding,
        val titleBill: (Int, String) -> String,
        val contentBill: (String, Int) -> String,
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
                titleTotalMoney.text = item.totalBill().toString().convertStrToMoney()
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
