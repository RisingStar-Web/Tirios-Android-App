package com.ai.tirios.preference

import android.content.Context
import android.content.SharedPreferences
import com.ai.tirios.services.PreferenceInfo

import javax.inject.Inject

class PreferencesHelper
@Inject
internal constructor(context: Context, @PreferenceInfo prefFileName: String) : IPreferencesHelper {

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getAll(): MutableMap<String, *> = mPrefs.all

    override fun getString(key: String) = mPrefs.getString(key, null) ?: "not found"

    override fun putString(key: String, value: String) = mPrefs.edit().putString(key, value).apply()

    override fun getInt(key: String) = mPrefs.getInt(key, 0)

    override fun putInt(key: String, value: Int) = mPrefs.edit().putInt(key, value).apply()

    override fun getBoolean(key: String) = mPrefs.getBoolean(key, false)

    override fun putBoolean(key: String, value: Boolean) =
        mPrefs.edit().putBoolean(key, value).apply()

    override fun getLong(key: String) = mPrefs.getLong(key, 0)

    override fun putLong(key: String, value: Long) = mPrefs.edit().putLong(key, value).apply()

    override fun clearPreferences() = mPrefs.edit().clear().apply()
}
