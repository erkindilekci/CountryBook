package com.erkindilekci.countrylist.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SpecialSharedPreferences {
    companion object {
        private val PREFERENCES_TIME = "preferences_time"
        private var sharedPreferences: SharedPreferences? = null
        private val lock = Any()

        @Volatile private var instance: SpecialSharedPreferences? = null
        operator fun invoke(context: Context): SpecialSharedPreferences = instance ?: synchronized(lock){
            instance ?: makeSpecialSharedPrefences(context).also {
                instance = it
            }
        }
        private fun makeSpecialSharedPrefences(context: Context): SpecialSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }
    fun saveTime(time: Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME, time)
        }
    }
    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME,0)
}