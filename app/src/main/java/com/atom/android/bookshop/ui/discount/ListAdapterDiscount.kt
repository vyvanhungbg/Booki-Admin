package com.atom.android.bookshop.ui.discount

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseAdapter
import com.atom.android.bookshop.base.BaseViewHolder
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.databinding.ItemDiscountBinding
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.loadImage

class ListAdapterDiscount(
    private val onClick: (Discount) -> Unit
) :
    BaseAdapter<Discount, BaseViewHolder<Discount>>(DiffCallBackItemDiscount()) {

    class DiffCallBackItemDiscount : DiffUtil.ItemCallback<Discount>() {
        override fun areItemsTheSame(oldItemSearch: Discount, newItemSearch: Discount): Boolean {
            return oldItemSearch.id == newItemSearch.id
        }

        override fun areContentsTheSame(oldItemSearch: Discount, newItemSearch: Discount): Boolean {
            return oldItemSearch == newItemSearch
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Discount> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDiscountBinding.inflate(inflater, parent, false)

        val contentDiscount = { code: String ->
            parent.context.getString(
                R.string.text_content_discount,
                code
            )
        }

        val timeStart = { timeStart: String ->
            parent.context.getString(
                R.string.text_time_start,
                timeStart
            )
        }

        val timeEnd = { timeEnd: String ->
            parent.context.getString(
                R.string.text_time_end,
                timeEnd
            )
        }


        return ViewHolder(
            binding,
            contentDiscount,
            timeStart,
            timeEnd
        )
    }


    inner class ViewHolder(
        val binding: ItemDiscountBinding,
        val contentDiscount: (String) -> String,
        val timeStart: (String) -> String,
        val timeEnd: (String) -> String,
    ) :
        BaseViewHolder<Discount>(binding, onClick) {
        override fun binView(item: Discount) {
            super.binView(item)
            binding.apply {
                textViewTitleDiscount.text = item.name
                if(item.code != Constants.DEFAULT_STRING){
                    textViewContentDiscount.text = contentDiscount(item.code)
                }else{
                    textViewContentDiscount.isVisible = false
                }
                textViewStartTime.text = timeStart(item.timeStart)
                textViewEndTime.text = timeEnd(item.timeEnd)
                imageDiscount.loadImage(Uri.parse(item.image))
            }
        }
    }
}
