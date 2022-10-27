package com.atom.android.bookshop.ui.discount

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.databinding.ItemDiscountBinding
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.loadImage

class ListAdapterDiscount(
    private val onClick: (Discount) -> Unit
) : BaseAdapter<Discount, BaseViewHolder<Discount>>(Discount.DiffCallBackItemDiscount()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Discount> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDiscountBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    
    inner class ViewHolder(val binding: ItemDiscountBinding) : BaseViewHolder<Discount>(binding) {
        override fun binView(item: Discount) {
            super.binView(item)
            binding.apply {
                textViewTitleDiscount.text = item.name
                if (item.code != Constants.DEFAULT_STRING) {
                    textViewContentDiscount.text = binding.root.context.getString(
                        R.string.text_content_discount,
                        item.code
                    )
                } else {
                    textViewContentDiscount.isVisible = false
                }
                textViewStartTime.text = binding.root.context.getString(
                    R.string.text_time_start,
                    item.timeStart
                )
                textViewEndTime.text = binding.root.context.getString(
                    R.string.text_time_end,
                    item.timeEnd
                )
                imageDiscount.loadImage(Uri.parse(item.image))
                itemView.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}
