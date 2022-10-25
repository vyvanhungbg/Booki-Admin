package com.atom.android.bookshop.utils

import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.TimeZone

fun convertStringToDate(string: String?): String {
    if (string == null || string.isEmpty()) return Constants.DEFAULT_STRING
    return try {
        var stringTime = string
        if (stringTime.contains(Constants.DOT)) {
            val indexOfDot = string.indexOf(Constants.DOT)
            stringTime = string.substring(Constants.FIRST_POSITION, indexOfDot)
                .plus(Constants.LAST_CHARACTER_TIME_UTC)
        }
        val inputFormat = SimpleDateFormat(Constants.FORMAT_DATE_TIME_INPUT) // UTC time
        val outputFormat = SimpleDateFormat(Constants.FORMAT_DATE_TIME) // local time
        inputFormat.timeZone = TimeZone.getTimeZone(ApiConstants.Response.TIMEZONE)
        val date = inputFormat.parse(stringTime)
        outputFormat.format(date).toString()
    } catch (ex: ParseException) {
        Constants.DEFAULT_STRING
    }
}
