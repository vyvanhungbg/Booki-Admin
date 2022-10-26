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

        val timeDone = { timeDone: String ->
            parent.context.getString(
                R.string.text_time_done,
                timeDone
            )
        }

        return ViewHolder(
            binding,
            titleBill,
            contentBill,
            timeConfirm,
            timeDelivery,
            timeDone
        )
    }


    inner class ViewHolder(
        val binding: ItemBillSuccessBinding,
        val titleBill: (Int, String) -> String,
        val contentBill: (String, Int) -> String,
        val timeConfirm: (String) -> String,
        val timeDelivery: (String) -> String,
        val timeDone: (String) -> String,
    ) :
        BaseViewHolder<Bill>(binding) {
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
                textViewTimeDone.text = timeDone(item.getTimeDone())
                titleTotalMoney.text = item.totalBill().toString().convertStrToMoney()
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
