package com.atom.android.bookshop.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(
    binding: ViewBinding,
    onClickItem: (T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var item: T? = null

    init {
        itemView.setOnClickListener {
            item?.let(onClickItem)
        }
    }

    open fun binView(item: T) {
        this.item = item
    }
}
