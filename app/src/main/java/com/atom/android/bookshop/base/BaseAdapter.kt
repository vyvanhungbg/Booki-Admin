package com.atom.android.bookshop.base

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atom.android.bookshop.utils.Constants

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtil) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binView(getItem(position))
    }

    fun addItem(bill: T) {
        val newList = currentList.toMutableList()
        newList.add(Constants.FIRST_POSITION, bill)
        submitList(newList)
    }

    fun loadMore(recyclerView: RecyclerView?, handle: () -> Unit) {
        recyclerView?.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    val sizeData = recyclerView.adapter?.itemCount?.minus(1)
                    if (linearLayoutManager != null &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == sizeData && sizeData != -1
                    ) {
                        handle()
                    }
                }
            })
        }
    }

}
