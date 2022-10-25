package com.atom.android.bookshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    val id: Int,
    val authorName: String,
    val description: String,
    val image: String
) : Parcelable {
    companion object {
        const val ID = "id"
        const val AUTHOR_NAME = "authorName"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
    }
}
