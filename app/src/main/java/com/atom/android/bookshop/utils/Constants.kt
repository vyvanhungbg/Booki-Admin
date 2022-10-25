package com.atom.android.bookshop.utils

object Constants {
    private const val PROJECT_NAME = "com.atom.android.bookshop."

    const val NUMBER_PHONE_PATTERN = "([0-9]{9,10})\\b"
    const val EMAIL_PATTERN = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$"
    const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"

    const val SHARED_PREF = PROJECT_NAME + "SHARED_PREF"
    const val SHARED_PREF_DEFAULT_STRING = ""
    const val SHARED_PREF_TOKEN_LOGIN = PROJECT_NAME + "SHARED_PREF_TOKEN_LOGIN"

    const val LINE_FEED = "\r\n"

    const val FIRST_POSITION = 0
    const val SIZE_SPAN_OF_TEXT = 1.2f
    const val SIZE_SPAN_OF_NUMBER = 0.8f
    const val SIZE_IMAGE = 120
    const val TIME_REQUEST_FORGOT_PASS = 60000L
    const val SECOND_TO_MIL = 1000L
    const val IS_TRUE = 1

    const val FORMAT_DATE_TIME = "dd-MM-yyyy '|' HH:mm:ss"
    const val FORMAT_DATE_TIME_INPUT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val LAST_CHARACTER_TIME_UTC = "Z"

    const val DEFAULT_INT = 0
    const val DEFAULT_DOUBLE = 0.0
    const val DEFAULT_STRING = ""
    const val DEFAULT_PAGE = 0
    const val DOT = '.'
    const val CURRENCY_UNIT = 'đ'

    const val SCHEME_ACTION_CALL = "tel"
}
