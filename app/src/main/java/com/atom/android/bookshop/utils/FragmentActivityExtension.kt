package com.atom.android.bookshop.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.atom.android.bookshop.R

fun FragmentActivity.navigate(fragment: Fragment) {
    val beginTransaction = this.supportFragmentManager?.beginTransaction()
    beginTransaction?.replace(R.id.fragment_container, fragment)
        ?.addToBackStack(fragment.tag)?.commit()
}
