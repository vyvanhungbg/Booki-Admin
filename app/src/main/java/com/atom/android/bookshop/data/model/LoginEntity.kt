package com.atom.android.bookshop.data.model

data class LoginEntity(val userEmail: String, val userPassword: String) {

    companion object {
        const val EMAIL = "userEmail"
        const val PASSWORD = "userPassword"
    }
}
