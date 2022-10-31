package com.atom.android.bookshop.utils

import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.api.ApiConstants.ERROR.ERROR_MESSAGE_FORBIDDEN
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException

fun httpConnection(
    formData: String?,
    uri: String,
    method: String = ApiConstants.Method.GET,
    token: String? = null
): String {
    val content = StringBuilder("")
    var connection: HttpURLConnection? = null

    try {
        val url = URL(uri)
        connection = url.openConnection() as HttpURLConnection
        with(connection) {
            requestMethod = method
            setRequestProperty(
                ApiConstants.ATTRIBUTE.CONTENT_TYPE,
                ApiConstants.ATTRIBUTE.APPLICATION_JSON
            )
            setRequestProperty(
                ApiConstants.ATTRIBUTE.ACCEPT,
                ApiConstants.ATTRIBUTE.APPLICATION_JSON
            )
            setRequestProperty(ApiConstants.ATTRIBUTE.CHARSET, ApiConstants.ATTRIBUTE.MODE_CHARSET)
            setRequestProperty(
                ApiConstants.ATTRIBUTE.AUTHORIZATION,
                "${ApiConstants.ATTRIBUTE.BEARER} $token"
            )
            connectTimeout = ApiConstants.CONNECTION_TIME
            if (method == ApiConstants.Method.POST || method == ApiConstants.Method.PUT) {
                doOutput = true
                writeJsonToBodyFrom(formData)
            }
            connect()
        }

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            content.append(connection.readJsonFromRequest())
        } else if (connection.responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
            throw HttpConnectionException(Exception(), ERROR_MESSAGE_FORBIDDEN)
        }
    } catch (ex: UnknownHostException) {
        throw HttpConnectionException(ex) // timeout
    } finally {
        connection?.disconnect()
    }
    return content.toString()
}

fun httpConnectionSendFormData(
    formData: String?,
    uri: String,
    method: String = ApiConstants.Method.GET,
    token: String? = null,
    typeBody: String = ApiConstants.ATTRIBUTE.APPLICATION_JSON
): String {
    val content = StringBuilder("")
    var connection: HttpURLConnection? = null

    try {
        val url = URL(uri)
        connection = url.openConnection() as HttpURLConnection
        with(connection) {
            requestMethod = method
            setRequestProperty(
                ApiConstants.ATTRIBUTE.CONTENT_TYPE,
                typeBody
            )
            setRequestProperty(
                ApiConstants.ATTRIBUTE.ACCEPT,
                ApiConstants.ATTRIBUTE.APPLICATION_JSON
            )
            setRequestProperty(ApiConstants.ATTRIBUTE.CHARSET, ApiConstants.ATTRIBUTE.MODE_CHARSET)
            setRequestProperty(
                ApiConstants.ATTRIBUTE.AUTHORIZATION,
                "${ApiConstants.ATTRIBUTE.BEARER} $token"
            )
            if (method == ApiConstants.Method.POST || method == ApiConstants.Method.PUT) {
                doOutput = true
                writeJsonToBodyFrom(formData)
            }

            connect()
        }

        content.append(connection.readJsonFromRequest())
    } catch (ex: UnknownHostException) {
        throw HttpConnectionException(ex) // timeout
    } catch (ex: FileNotFoundException) {
        throw HttpConnectionException(ex) // bad url
    } finally {
        connection?.disconnect()
    }
    return content.toString()
}

// write  json data in body request
private fun HttpURLConnection.writeJsonToBodyFrom(formData: String?) {
    formData?.let {
        this.outputStream.use { os ->
            val input: ByteArray = formData.toByteArray(Charsets.UTF_8)
            os.write(input, 0, input.size)
            os.flush()
            os.close()
        }
    }
}

// read output of request
private fun HttpURLConnection.readJsonFromRequest(): String {
    val content = StringBuilder("")
    val inputStreamReader = InputStreamReader(this.inputStream, Charsets.UTF_8)
    val bufferedReader = BufferedReader(inputStreamReader)
    bufferedReader.forEachLine {
        content.append(it)
    }
    inputStreamReader.close()
    return content.toString()
}
