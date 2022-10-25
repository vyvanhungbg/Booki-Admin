package com.atom.android.bookshop.utils

import android.content.Context
import android.content.SharedPreferences

class  SharedPreferenceUtils private constructor() {

    companion object {
        private var instance: SharedPreferences? = null
        fun getInstance(context: Context?) = synchronized(this) {
            instance ?: context?.getSharedPreferences(
                Constants.SHARED_PREF, Context.MODE_PRIVATE
            ).also {
                instance = it
            }
        }
    }
}

fun SharedPreferences.getString(key: String): String {
    this.also {
        return it.getString(key, Constants.SHARED_PREF_DEFAULT_STRING)
            ?: Constants.SHARED_PREF_DEFAULT_STRING
    }
}

fun SharedPreferences.putStringCommit(key: String, value: String?): Boolean {
    this.edit()?.also {
        it.putString(key, value)
        return it.commit()
    }
    return false
}

fun SharedPreferences.putStringApply(key: String, value: String?) {
    this.edit()?.also {
        it.putString(key, value)
        it.apply()
    }
}

fun SharedPreferences.getTokenLogin(): String {
    return this.getString(Constants.SHARED_PREF_TOKEN_LOGIN)
}

fun SharedPreferences.destroyToken() {
    this.edit().remove(Constants.SHARED_PREF_TOKEN_LOGIN).apply()
}
