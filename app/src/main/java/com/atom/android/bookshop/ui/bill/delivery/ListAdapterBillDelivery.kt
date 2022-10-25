package com.atom.android.bookshop.ui.bill.delivery

import android.view.LayoutInflater
import android.view.ViewGroup
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.databinding.ItemBillDeliveryBinding
import com.atom.android.bookshop.ui.bill.DiffCallBackItemBill
import com.atom.android.bookshop.utils.Constants

class ListAdapterBillDelivery(
    private val onClick: (Bill) -> Unit,
    private val onClickConfirm: (Bill) -> Unit,
    private val onClickCancel: (Bill) -> Unit
) :
    BaseAdapter<Bill, BaseViewHolder<Bill>>(DiffCallBackItemBill()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Bill> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBillDeliveryBinding.inflate(inflater, parent, false)
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

        val timeDelivery = { timeDelivery: String ->
            parent.context.getString(
                R.string.text_time_delivery,
                timeDelivery
            )
        }

        return ViewHolder(
            binding,
            titleBill,
            contentBill,
            timeConfirm,
            timeDelivery,
            onClickCancel,
            onClickConfirm
        )
    }


    inner class ViewHolder(
        val binding: ItemBillDeliveryBinding,
        val titleBill: (Int, String) -> String,
        val contentBill: (String, Int) -> String,
        val timeConfirm: (String) -> String,
        val timeDelivery: (String) -> String,
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
                textViewTimeDelivery.text = timeDelivery(item.getTimeDelivery())
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