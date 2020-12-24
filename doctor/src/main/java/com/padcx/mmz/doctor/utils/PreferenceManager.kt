package com.padcx.mmz.doctor.utils

import android.content.Context
import com.google.gson.Gson

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */

class PreferenceManager private constructor(
    private val context: Context,
    private val name: String = "default_pref"
) {
    companion object {
        /**
         * Use this value directly, it will be instantiated from the Application Instance
         */
        var INSTANCE: PreferenceManager? = null
            private set

        /**
         * don't call this method unless if you really want to create a new Instance
         *
         * @param context
         * @param name
         */
        fun newInstance(context: Context, name: String = "default_pref"): PreferenceManager? {
            INSTANCE = PreferenceManager(context, name)
            return INSTANCE
        }
    }

    fun getString(key: String, default: String? = null) = getPref().getString(key, default)
    fun putString(key: String, value: String?) = getEdit().putString(key, value).commit()

    fun getStringSet(key: String, default: MutableSet<String>? = null) =
        getPref().getStringSet(key, default)

    fun putStringSet(key: String, value: MutableSet<String>?) =
        getEdit().putStringSet(key, value).commit()


    fun getInt(key: String, default: Int = -1) = getPref().getInt(key, default)
    fun putInt(key: String, value: Int) = getEdit().putInt(key, value).commit()

    fun getLong(key: String, default: Long = -1L) = getPref().getLong(key, default)
    fun putLong(key: String, value: Long) = getEdit().putLong(key, value).commit()

    fun getFloat(key: String, default: Float = -1f) = getPref().getFloat(key, default)
    fun putFloat(key: String, value: Float) = getEdit().putFloat(key, value).commit()

    fun getBoolean(key: String, default: Boolean = false) = getPref().getBoolean(key, default)
    fun putBoolean(key: String, value: Boolean) = getEdit().putBoolean(key, value).commit()

    fun <T> putObject(key: String, value: T?) =
        getEdit().putString(key, Gson().toJson(value)).commit()

    inline fun <reified T : Any> getObject(key: String): T? {
        val jsonString = getPref().getString(key, "")
        return if (jsonString.isNullOrBlank()) null else Gson().fromJson(jsonString, T::class.java)
    }


    fun clear() = getEdit().clear().apply()

    fun getPref() = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    fun getEdit() = getPref().edit()
}