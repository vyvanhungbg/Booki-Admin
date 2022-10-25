package com.atom.android.bookshop.data.source.remote

interface IRequestCallback<T> {
    fun onSuccess(responseObject: T)
    fun onFailed(message: String?)
}
