package com.atom.android.bookshop.data.source.remote.api

import com.atom.android.bookshop.BuildConfig

object ApiURL {

    fun pathLogin(): String = BuildConfig.API_HOST + ApiConstants.Endpoint.LOGIN
    fun pathForgotEmail(): String = BuildConfig.API_HOST + ApiConstants.Endpoint.FORGOT_EMAIL
    fun pathOrder(): String = BuildConfig.API_HOST + ApiConstants.Endpoint.ORDER
    fun pathUpdateStatusBill(): String =
        BuildConfig.API_HOST + ApiConstants.Endpoint.UPDATE_STATUS_BILL
    fun pathGetUser(): String = BuildConfig.API_HOST + ApiConstants.Endpoint.GET_USER
    fun pathCheckToken(): String = BuildConfig.API_HOST + ApiConstants.Endpoint.GET_USER
    fun pathDiscount(): String = BuildConfig.API_HOST + ApiConstants.Endpoint.DISCOUNT
}
