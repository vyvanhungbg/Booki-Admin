package com.atom.android.bookshop.data.model

data class User(
    val id: Int,
    val name: String,
    val dateOfBirth: String,
    val email: String,
    val gender: String,
    val image: String,
    val phone: String,
    val updatedAt: String,
    val createdAt: String,
    val confirmEmail: Boolean
) {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val DATE_OF_BIRTH = "dateOfBirth"
        const val EMAIL = "email"
        const val GENDER = "gender"
        const val IMAGE = "image"
        const val PHONE = "phone"
        const val UPDATED_AT = "updatedAt"
        const val CREATED_AT = "createdAt"
        const val CONFIRM_EMAIL = "confirmEmail"
    }
}
