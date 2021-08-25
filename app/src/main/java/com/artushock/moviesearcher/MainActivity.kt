package com.artushock.moviesearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.press_btn).setOnClickListener{
            findViewById<TextView>(R.id.hello_tv).text = getString(R.string.button_pressed)
        }
    }
}