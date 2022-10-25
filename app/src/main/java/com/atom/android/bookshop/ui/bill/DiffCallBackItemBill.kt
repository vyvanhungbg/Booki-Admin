package com.atom.android.bookshop.ui.bill

import androidx.recyclerview.widget.DiffUtil
import com.atom.android.bookshop.data.model.Bill

class DiffCallBackItemBill : DiffUtil.ItemCallback<Bill>() {
    override fun areItemsTheSame(oldItemSearch: Bill, newItemSearch: Bill): Boolean {
        return oldItemSearch.id == newItemSearch.id
    }

    override fun areContentsTheSame(oldItemSearch: Bill, newItemSearch: Bill): Boolean {
        return oldItemSearch == newItemSearch
    }
}
