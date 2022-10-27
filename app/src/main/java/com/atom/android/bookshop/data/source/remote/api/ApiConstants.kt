package com.atom.android.bookshop.data.source.remote.api

object ApiConstants {

    object Endpoint {
        const val GET_USER = "user"
        const val BOOK = "book"
        const val AUTHOR = "author"
        const val GENRE = "genre"
        const val PUBLISHER = "publisher"
        const val LOGIN = "login"
        const val CHECK_LOGIN = "$LOGIN/check_login"
        const val FORGOT_EMAIL = "forgot_password"
        const val ORDER = "order"
        const val UPDATE_STATUS_BILL = "$ORDER/update_status"
        const val DISCOUNT = "discount"
    }

    object Method {
        const val POST = "POST"
        const val GET = "GET"
        const val PUT = "PUT"
        const val DELETE = "DELETE"
    }

    object Response {
        const val DATA = "data"
        const val STATUS = "status"
        const val MESSAGE = "message"
        const val TIMEZONE = "UTC"
    }

    const val CONNECTION_TIME = 5000

    object ERROR {
        const val ERROR_MESSAGE_TIMEOUT = "Lỗi kết nối server"
        const val ERROR_MESSAGE_BAD_URL = "Kết nối không hợp lệ"
        const val ERROR_MESSAGE_IO = "Dữ liệu không hợp lệ"
        const val ERROR_MESSAGE_JSON_CONVERT_FAILED = "Dữ liệu không hợp lệ"
        const val ERROR_MESSAGE_FORBIDDEN = "Không có quyền truy cập"
    }

    object FIELD {
        const val EMAIL = "email"
        const val PAGE = "page"
        const val TYPE = "type"
        const val REASON = "reason"
    }

    object ATTRIBUTE {
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"
        const val APPLICATION_JSON_FORM = "application/x-www-form-urlencoded"
        const val ACCEPT = "Accept"
        const val CHARSET = "charset"
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
        const val MODE_CHARSET = "utf-8"
    }

    object TYPEOFBILL {
        const val PENDING = 1
        const val ACCEPT = 2
        const val DELIVERY = 3
        const val SUCCESS = 4
        const val DESTROY = 5
        const val LOST = 6
    }

    object TYPEOFDISCOUNT {
        const val RUNNING = 0
        const val EXPIRED = 1
        const val DESTROY = 2
        const val PENDING = 3
    }

    object DIALOGCONFIG {
        const val MARGIN_Y = -170
    }
}
