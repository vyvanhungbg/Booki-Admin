package com.atom.android.bookshop.ui.bill.detail

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.OrderLine
import com.atom.android.bookshop.databinding.ItemOrderLineBinding
import com.atom.android.bookshop.utils.convertStrToMoney
import com.atom.android.bookshop.utils.loadImage

class ListAdapterBillDetail(
    private val onClick: (OrderLine) -> Unit,
) :
    BaseAdapter<OrderLine, BaseViewHolder<OrderLine>>(DiffCallBackItemOrderLine()) {

    class DiffCallBackItemOrderLine : DiffUtil.ItemCallback<OrderLine>() {
        override fun areItemsTheSame(oldItemSearch: OrderLine, newItemSearch: OrderLine): Boolean {
            return oldItemSearch.book.id == newItemSearch.book.id
        }

        override fun areContentsTheSame(
            oldItemSearch: OrderLine,
            newItemSearch: OrderLine
        ): Boolean {
            return oldItemSearch == newItemSearch
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<OrderLine> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderLineBinding.inflate(inflater, parent, false)
        val textAmount = { textAmount: Int ->
            parent.context.getString(
                R.string.text_amount,
                textAmount
            )
        }

        return ViewHolder(
            binding,
            onClick,
            textAmount
        )
    }


    inner class ViewHolder(
        val binding: ItemOrderLineBinding,
        val onClick: (OrderLine) -> Unit,
        val textAmount: (Int) -> String
    ) :
        BaseViewHolder<OrderLine>(binding, onClick) {
        override fun binView(item: OrderLine) {
            super.binView(item)
            binding.apply {
                imgItem.loadImage(Uri.parse(item.book.image))
                textViewPriceItem.text = item.price.toString().convertStrToMoney()
                textViewAmountItem.text = textAmount(item.amount)
                textViewNameItem.text = item.book.title
            }
        }
    }

}
