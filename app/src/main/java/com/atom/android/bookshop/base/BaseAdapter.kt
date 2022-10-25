package com.atom.android.bookshop.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.utils.Constants

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtil) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binView(getItem(position))
    }

    fun removeItem(item: T) {
        val newList = currentList.toMutableList()
        newList.remove(item)
        submitList(newList)
    }

    fun addList(list: List<T>) {
        val newList = currentList.toMutableList()
        newList.addAll(list)
        submitList(newList)
    }

    fun addItem(bill: T){
        val newList = currentList.toMutableList()
        newList.add(Constants.FIRST_POSITION,bill)
        submitList(newList)
    }

    fun clearAll(){
        val newList = mutableListOf<T>()
        submitList(newList)
    }
}
