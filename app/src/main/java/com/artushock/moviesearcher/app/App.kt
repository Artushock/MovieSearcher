package com.artushock.moviesearcher.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import com.artushock.moviesearcher.model.room.SeenMoviesDao
import com.artushock.moviesearcher.model.room.SeenMoviesDataBase
import com.artushock.moviesearcher.view.fragments.APP_PREFERENCES_KEY
import com.google.firebase.messaging.FirebaseMessaging

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.d("123123123", "token ${task.result.toString()}")
            }
        }
    }

    companion object {
        private var appInstance: App? = null
        private var sharedPreferences: SharedPreferences? = null

        private var db: SeenMoviesDataBase? = null
        private const val DB_NAME = "SeenMovies.db"

        fun getAppPreferences(): SharedPreferences? {
            if (sharedPreferences == null) {
                if (appInstance == null) {
                    throw IllegalStateException("Application is null while getting SharedPreferences")
                } else {
                    sharedPreferences = appInstance?.applicationContext?.getSharedPreferences(
                        APP_PREFERENCES_KEY,
                        Context.MODE_PRIVATE
                    )
                }
            }
            return sharedPreferences
        }

        fun getSeenMoviesDao(): SeenMoviesDao {
            if (db == null) {
                synchronized(SeenMoviesDataBase::class) {
                    if (db == null) {
                        if (appInstance == null) {
                            throw IllegalStateException("Application is null while creating DataBase")
                        } else {
                            db = Room.databaseBuilder(
                                appInstance!!.applicationContext,
                                SeenMoviesDataBase::class.java,
                                DB_NAME
                            )
                                //.allowMainThreadQueries()
                                .build()
                        }
                    }
                }
            }
            return db!!.seenMoviesDao()
        }
    }
}