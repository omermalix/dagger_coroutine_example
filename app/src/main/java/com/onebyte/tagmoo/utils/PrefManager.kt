package com.onebyte.tagmoo.utils

import android.content.Context
import androidx.preference.PreferenceManager

class PrefManager() {
    companion object {
        // Shared preferences file name
        private val PREF_NAME = "tagMoo"

        /**
         * Save date in preferences
         */
        fun savePreferences(key: String, value: String, context: Context) {
            val sp = PreferenceManager
                .getDefaultSharedPreferences(context)
            val edit = sp.edit()
            edit.putString(key, value)
            edit.apply()
        }

        /**
         * Save boolean date in preferences
         */
        fun saveBooleanPreferences(key: String, value: Boolean, context: Context) {
            val sp = PreferenceManager
                .getDefaultSharedPreferences(context)
            val edit = sp.edit()
            edit.putBoolean(key, value)
            edit.apply()
        }

        /**
         * Load date in preferences
         */
        fun loadPreferences(context: Context, Key: String): String? {
            val sp = PreferenceManager
                .getDefaultSharedPreferences(context)
            return sp.getString(Key, "0")

        }

        /**
         * Load boolean date in preferences
         */
        fun loadBooleanPreferences(context: Context, Key: String): Boolean {
            val sp = PreferenceManager
                .getDefaultSharedPreferences(context)
            return sp.getBoolean(Key, false)

        }

        /**
         * Remove specific date from preferences
         */
        fun removePrefrence(context: Context, key: String) {
            val myFavePref = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = myFavePref.edit()
            editor.remove(key)
            editor.apply()
        }
    }

}