package com.atom.android.bookshop.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import com.atom.android.bookshop.R
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
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

fun convertDataTimeToInstant(year: Int, month: Int, day: Int, hour: Int, minute: Int): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val pickedDateTime = Calendar.getInstance()
        pickedDateTime.set(year, month, day, hour, minute)
        pickedDateTime.toInstant().toString()
    } else {
        val seconds = "00"
        val instant: String =
            "$year-$month-${day}T${hour}:${minute}:${seconds}${Constants.LAST_CHARACTER_TIME_UTC}";
        instant
    }
}
