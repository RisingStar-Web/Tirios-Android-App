package utility

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Lenovo on 10-05-2018.
 */
object ObjectPreference {

    val PREF_NAME = "ObjectPreference"
    val MODE = Context.MODE_PRIVATE

    fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, MODE)
    }

    fun getEditor(context: Context): SharedPreferences.Editor {
        return getPreferences(context).edit()
    }

    fun writeString(context: Context, key: String, value: String) {
        getEditor(context).putString(key, value).commit()

    }

    fun readString(context: Context, key: String, defValue: String): String? {
        return getPreferences(context).getString(key, defValue)
    }


    fun writeBoolean(context: Context, key: String, value: Boolean) {
        getEditor(context).putBoolean(key, value).commit()
    }

    fun readBoolean(context: Context, key: String, defValue: Boolean): Boolean {
        return getPreferences(context).getBoolean(key, defValue)
    }


    fun removeSpecficobject(context: Context, key: String): Boolean {
        return getEditor(context).remove(key).commit()
    }

    fun removeAllObject(context: Context): Boolean {
        return getEditor(context).clear().commit()
    }

    fun writeInteger(context: Context, key: String, value: Int) {
        getEditor(context).putInt(key, value).commit()

    }

    fun readInteger(context: Context, key: String, defValue: Int): Int {
        return getPreferences(context).getInt(key, defValue)
    }


    fun writeFloat(context: Context, key: String, value: Float) {
        getEditor(context).putFloat(key, value).commit()

    }

    fun readFloat(context: Context, key: String, defValue: Float): Float {
        return getPreferences(context).getFloat(key, defValue)
    }

}