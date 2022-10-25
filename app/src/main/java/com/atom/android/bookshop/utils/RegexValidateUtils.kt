package com.atom.android.bookshop.utils

import com.atom.android.bookshop.utils.Constants.EMAIL_PATTERN
import com.atom.android.bookshop.utils.Constants.NUMBER_PHONE_PATTERN
import com.atom.android.bookshop.utils.Constants.PASS_PATTERN
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.regex.Pattern

fun isPhoneNumber(phoneNumber: String?): Boolean {
    return Pattern.matches(NUMBER_PHONE_PATTERN, phoneNumber)
}

fun isEmail(email: String?): Boolean {
    if (email.isNullOrEmpty())
        return false
    return Pattern.matches(EMAIL_PATTERN, email)
}

fun isPassword(pass: String?): Boolean {
    return Pattern.matches(PASS_PATTERN, pass)
}

fun String.convertStrToMoney(): String? {
    val nf: NumberFormat = NumberFormat.getCurrencyInstance()
    val dfs = DecimalFormatSymbols()
    dfs.currencySymbol = Constants.DEFAULT_STRING
    dfs.groupingSeparator = Constants.DOT
    nf.minimumFractionDigits = 0
    nf.roundingMode = RoundingMode.HALF_UP
    (nf as DecimalFormat).setDecimalFormatSymbols(dfs)
    return java.lang.String.valueOf(nf.format(BigDecimal(this))) + Constants.CURRENCY_UNIT
}
