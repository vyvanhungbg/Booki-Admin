package com.atom.android.bookshop.data.source.remote

data class ResponseObject<T>(val status: Boolean, val message: String, val data: T?)
