package com.atom.android.bookshop.data.model

data class Publisher(
    val id: Int,
    val publisherName: String
) {
    companion object {
        const val ID = "id"
        const val PUBLISHER_NAME = "publisherName"
    }
}
