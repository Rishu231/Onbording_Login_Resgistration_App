package com.example.softwarelab.sharedPreference


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.softwarelab.sharedPreference.SharedPrefConstants.LOGIN_COMPLETE
import com.example.softwarelab.sharedPreference.SharedPrefConstants.ONBOARDING_COMPLETE
import com.example.softwarelab.sharedPreference.SharedPrefConstants.SIGNUP_COMPLETE
import com.example.softwarelab.sharedPreference.SharedPrefConstants.SOCIAL_TOKEN
import com.example.softwarelab.sharedPreference.SharedPrefConstants.TOKEN
import com.google.gson.Gson


class PrefsUtil(val context: Context) {

    val TAG = PrefsUtil::class.java.simpleName
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Pref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    var onboarding_complete: Boolean
        get() = sharedPreferences[ONBOARDING_COMPLETE]?: false
        set(value) = sharedPreferences.set(ONBOARDING_COMPLETE, value)

    var signup_complete: Boolean
        get() = sharedPreferences[SIGNUP_COMPLETE]?: false
        set(value) = sharedPreferences.set(SIGNUP_COMPLETE, value)

    var login_complete: Boolean
        get() = sharedPreferences[LOGIN_COMPLETE]?: false
        set(value) = sharedPreferences.set(LOGIN_COMPLETE, value)

    var social_token: String
        get() = sharedPreferences[SOCIAL_TOKEN]?: ""
        set(value) = sharedPreferences.set(SOCIAL_TOKEN, value)

    var token: String
        get() = sharedPreferences[TOKEN]?: ""
        set(value) = sharedPreferences.set(TOKEN, value)



    fun deletePreferences() {
        editor.clear()
        editor.apply()
    }



    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> Log.e(TAG, "Setting shared pref failed for key: $key and value: $value ")
        }
    }


    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String ?: "") as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: 0L) as T?

            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

}
