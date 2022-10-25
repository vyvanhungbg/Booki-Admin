package com.atom.android.bookshop.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.atom.android.bookshop.R

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(resource: Int) {
    Toast.makeText(this, resource, Toast.LENGTH_SHORT).show()
}

fun Context.textSpannable(text: String?, number: String): Spannable {
    val spannable = SpannableString("$text $number")
    val sizeSpanOfText = RelativeSizeSpan(Constants.SIZE_SPAN_OF_TEXT)
    val sizeSpanOfNumber = RelativeSizeSpan(Constants.SIZE_SPAN_OF_NUMBER)
    text?.let {
        spannable.setSpan(
            sizeSpanOfText,
            Constants.FIRST_POSITION,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            sizeSpanOfNumber,
            Constants.FIRST_POSITION,
            number.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val purple: Int = ContextCompat.getColor(this, R.color.red)
        val teal: Int = ContextCompat.getColor(this, R.color.black)
        spannable.setSpan(
            ForegroundColorSpan(purple),
            Constants.FIRST_POSITION,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(teal),
            Constants.FIRST_POSITION,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannable
}
