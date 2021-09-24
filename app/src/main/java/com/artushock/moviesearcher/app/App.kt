package com.artushock.moviesearcher.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.artushock.moviesearcher.view.fragments.APP_PREFERENCES_KEY

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var sharedPreferences: SharedPreferences? = null

        fun getAppPreferences(): SharedPreferences? {
            if (sharedPreferences == null) {
                if (appInstance == null) {
                    throw IllegalAccessError("WTF?")
                } else {
                    sharedPreferences = appInstance?.applicationContext?.getSharedPreferences(
                        APP_PREFERENCES_KEY,
                        Context.MODE_PRIVATE
                    )
                }
            }
            return sharedPreferences
        }
    }
}