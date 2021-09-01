package com.artushock.moviesearcher

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    val TAG = "[Art_MainActivity]"

    lateinit var movieAdapter: MoviesAdapter
    var genre: MovieGenre = MovieGenre.UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()

    }

    private fun initView() {
        initTextView()
        initRecyclerView()
        initButtons()
    }

    private fun initTextView() {
        val mainTextView = findViewById<TextView>(R.id.hello_tv)
        mainTextView.setText("В списке ${MovieList.movies.size} фильмов")
    }

    private fun initButtons() {

        findViewById<Button>(R.id.clear_btn).setOnClickListener {
            MovieList.movies.clear()
            movieAdapter.notifyDataSetChanged()
        }

        //add button
        findViewById<Button>(R.id.add_btn).setOnClickListener {
            val addDAlertView = this.layoutInflater.inflate(R.layout.add_alert_view, null, false)


            initAddAlertSpinner(addDAlertView)

            val slider: Slider = addDAlertView.findViewById(R.id.rating_slider)
            var rating: Int = 5
            //slider.value = rating as Float
            slider.addOnChangeListener { slider, value, fromUser ->
                rating = value.toInt()
            }


            this.let {
                val builder = AlertDialog.Builder(it)

                builder.setView(addDAlertView)
                        .setTitle("Add movie")
                        .setMessage("Fill fields please")
                        .setPositiveButton(R.string.add_str
                        ) { _, _ ->
                            val name = getAddAlertName(addDAlertView)
                            MovieList.movies.add(Movie(name, genre, rating))
                        }
                        .setNegativeButton(R.string.cancel_str
                        ) { _, _ ->

                        }
                builder.show()
            }

            movieAdapter.notifyDataSetChanged()
        }
    }

    private fun getAddAlertName(addDAlertView: View): String {
        val editText: EditText = addDAlertView.findViewById(R.id.movie_name_et)
        val name = editText.text.toString().trim()
        return name
    }

    private fun initAddAlertSpinner(addDAlertView: View) {

        val items = getGenresStringList()

        val spinner: Spinner = addDAlertView.findViewById(R.id.genre_spinner);
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                genre = getGenreByString(items[position])
                Log.d(TAG, "onItemSelected - ${items[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                genre = MovieGenre.UNKNOWN
                Log.d(TAG, "onNothingSelected")
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                genre = getGenreByString(items[position])
                Log.d(TAG, "onItemClick - ${items[position]}")
            }
        }
    }

    private fun initRecyclerView() {
        val movieRecyclerView: RecyclerView = findViewById(R.id.movie_list_rv)
        movieRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        movieRecyclerView.layoutManager = layoutManager

        movieAdapter = MoviesAdapter(MovieList)

        movieRecyclerView.adapter = movieAdapter
    }

    private fun initData() {
        MovieList.movies.add(Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10))
        MovieList.movies.add(Movie("Крёстный отец", MovieGenre.ACTION, 10))
        MovieList.movies.add(Movie("Начало", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Форрест Гамп", MovieGenre.DRAMA, 10))
        MovieList.movies.add(Movie("Матрица", MovieGenre.ACTION, 10))
        MovieList.movies.add(Movie("Пролетая над гнездом кукушки", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Город Бога", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Молчание ягнят", MovieGenre.DRAMA, 10))
        MovieList.movies.add(Movie("Леон", MovieGenre.ACTION, 10))
        MovieList.movies.add(Movie("Американская история Икс", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Касабланка", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Однажды на Диком Западеа", MovieGenre.WESTERN, 8))
        MovieList.movies.add(Movie("Чужой", MovieGenre.ACTION, 8))
        MovieList.movies.add(Movie("Великий диктатор", MovieGenre.COMEDY, 9))
        MovieList.movies.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9))
    }
}