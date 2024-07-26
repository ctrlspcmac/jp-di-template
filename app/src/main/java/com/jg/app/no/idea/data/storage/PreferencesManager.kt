package com.jg.app.no.idea.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.my_weather_app.presentation.weather.models.HistoryModel
import com.jg.app.no.idea.constants.PreferencesConstants
import com.jg.app.no.idea.constants.PreferencesConstants.KEY_HISTORY_LIST
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesManager @Inject constructor
    (private val context: Context,
     private val moshi: Moshi) {

    private val preferencesName = PreferencesConstants.PREF_NAME;
    private val preferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, "")
    }

    fun saveInt(key: String, value: Int) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String): Int {
        return preferences.getInt(key, 0)
    }

    fun saveBoolean(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun saveLong(key: String, value: Long) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(key: String): Long {
        return preferences.getLong(key, 0)
    }

    fun saveFloat(key: String, value: Float) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String): Float {
        return preferences.getFloat(key, 0.0F)
    }

    // Object saving. This can't be used for list type
    suspend fun <T> saveObject(
        key: String,
        value: T,
        valueType: Class<T>
    ) {
        withContext(Dispatchers.IO) {
            val editor: SharedPreferences.Editor = preferences.edit()
            val json: String = moshi.adapter(valueType)?.toJson(value) ?: ""
            editor.putString(key, json)
            editor.apply()
        }
    }

    // Read function for JSON objects
    suspend fun <T> readObject(
        key: String,
        defaultValue: T?,
        returnType: Class<T>
    ): T? {
        var value: Any?
        withContext(Dispatchers.IO) {
            val json: String? = preferences.getString(key, defaultValue as String?)
            value = if (json == null)  {
                null
            } else {
                moshi.adapter(returnType)?.fromJson(json)
            }
        }
        return value as T?
    }

    // we're using coroutines here to cater to all data types including lists
    suspend fun clearObject(key: String) {
        withContext(Dispatchers.IO) {
            preferences.edit()
                .remove(key)
                .apply()
        }
    }

    fun clearString(key: String) {
        preferences.edit()
            .remove(key)
            .apply()
    }

    suspend fun saveHistoryList(dList: List<HistoryModel>) {
        val types = Types.newParameterizedType(List::class.java, HistoryModel::class.java)
        val jsonAdapter = moshi.adapter<List<HistoryModel>>(types)
        val json = jsonAdapter.toJson(dList)
        withContext(Dispatchers.IO) {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString(KEY_HISTORY_LIST, json)
            editor.apply()
        }
    }

    suspend fun readeHistoryList(): List<HistoryModel>? {
        var json: String?

        withContext(Dispatchers.IO) {
            json = preferences.getString(KEY_HISTORY_LIST, null)
        }

        val types = Types.newParameterizedType(List::class.java, HistoryModel::class.java)

        return if(json == null) {
            null
        } else {
            val jsonAdapter = moshi.adapter<List<HistoryModel>>(types)
            val result = json?.let { jsonAdapter.fromJson(it) }
            result
        }
    }

}

