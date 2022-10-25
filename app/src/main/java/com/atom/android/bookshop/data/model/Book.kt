package com.atom.android.bookshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int,
    val image: String,
    val isbn: String,
    val description: String,
    val genres: List<Genre>?,
    val language: String?,
    val numPages: Int?,
    val price: Double,
    val publicationDate: String?,
    val title: String,
    val availableQuantity: Int?,
    val bookAuthors: List<Author>?
) : Parcelable {
    companion object {
        const val ID = "id"
        const val IMAGE = "image"
        const val ISBN = "isbn"
        const val DESCRIPTION = "description"
        const val GENRES = "genres"
        const val LANGUAGE = "language"
        const val NUM_PAGES = "numPages"
        const val PRICE = "price"
        const val PUBLICATION_DATE = "publicationDate"
        const val PUBLISHER = "publisher"
        const val TITLE = "title"
        const val AVAILABLE_QUANTITY = "availableQuantity"
        const val BOOK_AUTHORS = "bookAuthors"
    }
}
