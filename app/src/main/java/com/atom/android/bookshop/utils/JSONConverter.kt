package com.atom.android.bookshop.utils

import com.atom.android.bookshop.data.model.*
import org.json.JSONArray
import org.json.JSONObject

fun LoginEntity.convertToJson(): String {
    val jsonObject = JSONObject()
    val strJson =
        jsonObject.put(LoginEntity.EMAIL, this.userEmail)
    jsonObject.put(LoginEntity.PASSWORD, this.userPassword)
    return strJson.toString()
}

fun JSONObject.getUser(): User {
    val confirmEmail = this.getInt(User.CONFIRM_EMAIL) ?: Constants.DEFAULT_INT
    val createdAt = convertStringToDate(this.getString(User.CREATED_AT) ?: Constants.DEFAULT_STRING)
    val dateOfBirth =
        convertStringToDate(this.getString(User.DATE_OF_BIRTH) ?: Constants.DEFAULT_STRING)
    val email = this.getString(User.EMAIL) ?: Constants.DEFAULT_STRING
    val gender = this.getString(User.GENDER) ?: Constants.DEFAULT_STRING
    val id = this.getInt(User.ID) ?: Constants.DEFAULT_INT
    val image = this.getString(User.IMAGE) ?: Constants.DEFAULT_STRING
    val name = this.getString(User.NAME) ?: Constants.DEFAULT_STRING
    val phone = this.getString(User.PHONE) ?: Constants.DEFAULT_STRING
    val updatedAt = convertStringToDate(this.getString(User.UPDATED_AT) ?: Constants.DEFAULT_STRING)
    return User(
        id,
        name,
        dateOfBirth,
        email,
        gender,
        image,
        phone,
        updatedAt,
        createdAt,
        confirmEmail == Constants.IS_TRUE
    )
}

fun JSONObject.getBill(): Bill {

    val id = this.getInt(User.ID) ?: Constants.DEFAULT_INT

    val address = this.getString(Bill.ADDRESS) ?: Constants.DEFAULT_STRING
    val note = this.getString(Bill.NOTE) ?: Constants.DEFAULT_STRING
    val phone = this.getString(Bill.PHONE) ?: Constants.DEFAULT_STRING
    val receiver = this.getString(Bill.RECEIVER) ?: Constants.DEFAULT_STRING
    val createdAt = convertStringToDate(this.getString(Bill.CREATED_AT) ?: Constants.DEFAULT_STRING)

    val jsonShippingMethod =
        JSONObject(this.getString(Bill.SHIPPING_METHOD) ?: Constants.DEFAULT_STRING)
    val shippingMethod = jsonShippingMethod.getShippingMethod()

    val jsonHistoryArray =
        JSONArray(this.getString(Bill.ORDER_HISTORIES) ?: Constants.DEFAULT_STRING)
    val orderHistoryList = mutableListOf<OrderHistory>()
    for (i in 0 until jsonHistoryArray.length()) {
        val jsonObjectHistory = jsonHistoryArray.getJSONObject(i)
        val orderHistory = jsonObjectHistory.getOrderHistory()
        orderHistoryList.add(orderHistory)
    }
    val orderLines = mutableListOf<OrderLine>()
    val jsonOrderLineArray =
        JSONArray(this.getString(Bill.ORDER_LINES) ?: Constants.DEFAULT_STRING)
    for (i in 0 until jsonOrderLineArray.length()) {
        val jsonObjectOrderLine = jsonOrderLineArray.getJSONObject(i)
        val orderLine = jsonObjectOrderLine.getOrderLine()
        orderLines.add(orderLine)
    }
    return Bill(
        id,
        shippingMethod, address, note, phone, receiver, orderHistoryList, orderLines, createdAt
    )
}

fun JSONObject.getShippingMethod(): ShippingMethod {
    val cost = this.getDouble(ShippingMethod.COST) ?: Constants.DEFAULT_DOUBLE
    val distanceAbove = this.getDouble(ShippingMethod.COST) ?: Constants.DEFAULT_DOUBLE
    val id = this.getInt(ShippingMethod.ID) ?: Constants.DEFAULT_INT
    val name = this.getString(ShippingMethod.NAME) ?: Constants.DEFAULT_STRING
    return ShippingMethod(id, name, cost, distanceAbove)
}

fun JSONObject.getOrderHistory(): OrderHistory {
    val reason = this.getString(OrderHistory.REASON) ?: Constants.DEFAULT_STRING
    val statusDate =
        convertStringToDate(this.getString(OrderHistory.STATUS_DATE) ?: Constants.DEFAULT_STRING)
    val jsonObjectStatus =
        JSONObject(this.getString(OrderHistory.STATUS) ?: Constants.DEFAULT_STRING)
    val status = jsonObjectStatus.getStatus()
    return OrderHistory(reason, status, statusDate)
}

fun JSONObject.getStatus(): Status {
    val id = this.getInt(Status.ID) ?: Constants.DEFAULT_INT
    val statusValue = this.getString(Status.STATUS_VALUE) ?: Constants.DEFAULT_STRING
    return Status(id, statusValue)
}

fun JSONObject.getOrderLine(): OrderLine {
    val amount = this.getInt(OrderLine.AMOUNT) ?: Constants.DEFAULT_INT
    val jsonObjectBook = JSONObject(this.getString(OrderLine.BOOK) ?: Constants.DEFAULT_STRING)
    val book = jsonObjectBook.getBook()
    val price = this.getDouble(OrderLine.PRICE) ?: Constants.DEFAULT_DOUBLE
    return OrderLine(amount, book, price)
}

fun JSONObject.getBook(): Book {
    val id = this.getInt(Book.ID) ?: Constants.DEFAULT_INT
    val title = this.getString(Book.TITLE) ?: Constants.DEFAULT_STRING
    val image = this.getString(Book.IMAGE) ?: Constants.DEFAULT_STRING
    val description = this.getString(Book.DESCRIPTION) ?: Constants.DEFAULT_STRING
    val isbn = this.getString(Book.ISBN) ?: Constants.DEFAULT_STRING
    val price = this.getDouble(Book.PRICE) ?: Constants.DEFAULT_DOUBLE
    val numberPage = this.getInt(Book.NUM_PAGES) ?: Constants.DEFAULT_INT
    val gender = mutableListOf<Genre>()
    val author = mutableListOf<Author>()
    val language = Constants.DEFAULT_STRING
    val publisherDate = Constants.DEFAULT_STRING
    val availableQuantity = this.getInt(Book.AVAILABLE_QUANTITY) ?: Constants.DEFAULT_INT
    return Book(
        id,
        image,
        isbn,
        description,
        gender,
        language,
        numberPage,
        price,
        publisherDate,
        title,
        availableQuantity,
        author
    )
}

fun JSONObject.getDiscount(): Discount {
    val id = this.getInt(Discount.ID) ?: Constants.DEFAULT_INT
    val name = this.getString(Discount.NAME) ?: Constants.DEFAULT_STRING
    val value = this.getDouble(Discount.VALUE) ?: Constants.DEFAULT_DOUBLE
    val code = this.getString(Discount.CODE) ?: Constants.DEFAULT_STRING
    val amount = this.getInt(Discount.AMOUNT) ?: Constants.DEFAULT_INT
    val image = this.getString(Discount.IMAGE) ?: Constants.DEFAULT_STRING
    val createdAt =
        convertStringToDate(this.getString(Discount.CREATED_AT) ?: Constants.DEFAULT_STRING)
    val timeEnd = convertStringToDate(this.getString(Discount.TIME_END) ?: Constants.DEFAULT_STRING)
    val timeStart =
        convertStringToDate(this.getString(Discount.TIME_START) ?: Constants.DEFAULT_STRING)
    val isVisible = this.getInt(Discount.IS_VISIBLE) ?: Constants.DEFAULT_INT
    val enabled = this.getInt(Discount.ENABLED) ?: Constants.DEFAULT_INT
    val stringJsonBook =
        JSONArray(this.getString(Discount.BOOK_DISCOUNTS) ?: Constants.DEFAULT_STRING)
    val books = mutableListOf<Book>()
    for (i in 0 until stringJsonBook.length()) {
        val jsonObjectBook = stringJsonBook.getJSONObject(i)
        val book = jsonObjectBook.getBook()
        books.add(book)
    }
    return Discount(
        id,
        name,
        value,
        code,
        amount,
        image,
        createdAt,
        timeEnd,
        timeStart,
        isVisible,
        enabled,
        books
    )
}

fun DiscountEntity.convertToJson(): String {
    val jsonObject = JSONObject()
    jsonObject.put(DiscountEntity.NAME, name)
    jsonObject.put(DiscountEntity.VALUE, value)
    jsonObject.put(DiscountEntity.CODE, code)
    jsonObject.put(DiscountEntity.AMOUNT, amount)
    jsonObject.put(DiscountEntity.IMAGE, image)
    jsonObject.put(DiscountEntity.TIME_END, timeEnd)
    jsonObject.put(DiscountEntity.TIME_START, timeStart)
    jsonObject.put(DiscountEntity.IS_VISIBLE, isVisible)
    jsonObject.put(DiscountEntity.ID_BOOK, idBook)
    return jsonObject.toString()
}
