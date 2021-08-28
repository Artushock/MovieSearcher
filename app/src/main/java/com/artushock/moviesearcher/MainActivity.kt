package com.artushock.moviesearcher

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.MovieGenre
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    val TAG = "[Art_MainActivity]"

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}