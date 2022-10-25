package com.atom.android.bookshop.utils

import com.atom.android.bookshop.data.source.remote.api.ApiConstants

class HttpConnectionException(
    ex: Exception,
    message: String? = ApiConstants.ERROR.ERROR_MESSAGE_TIMEOUT,
) : Exception(message, ex)

class JSONConvertException(
    ex: Exception,
    message: String? = ApiConstants.ERROR.ERROR_MESSAGE_JSON_CONVERT_FAILED
) : Exception(message, ex)
