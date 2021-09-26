package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.app.App
import com.artushock.moviesearcher.model.MovieCategory
import com.artushock.moviesearcher.model.MovieListState
import com.artushock.moviesearcher.model.dto.GenresDTO
import com.artushock.moviesearcher.model.dto.MoviesDTO
import com.artushock.moviesearcher.model.repositories.RemoteDataSource
import com.artushock.moviesearcher.model.repositories.RepositoryMovies
import com.artushock.moviesearcher.model.repositories.RepositoryMoviesImpl
import com.artushock.moviesearcher.view.fragments.ADULT_CONTENT_SHOW_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    val nowPlayingMoviesToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    val popularMoviesToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    val topRatedMoviesToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    private val repository: RepositoryMovies = RepositoryMoviesImpl(RemoteDataSource()),
) : ViewModel() {

    private var genresList = GenresDTO(listOf())

    fun getMovies() {
        getGenresList()
        getMoviesByCategory(nowPlayingMoviesToObserve, MovieCategory.NOW_PLAYING)
        getMoviesByCategory(popularMoviesToObserve, MovieCategory.POPULAR)
        getMoviesByCategory(topRatedMoviesToObserve, MovieCategory.TOP_RATED)
    }

    private fun getMoviesByCategory(
        observe: MutableLiveData<MovieListState>,
        movieCategory: MovieCategory
    ) {

        val callback = object : Callback<MoviesDTO> {
            override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
                val serverResponse: MoviesDTO? = response.body()
                addGenresNameToResponse(serverResponse)
                observe.postValue(
                    if (response.isSuccessful && serverResponse != null) {
                        val moviesToShow: MoviesDTO = checkMovies(serverResponse)

                        MovieListState.Success(
                            moviesToShow,
                            movieCategory
                        )
                    } else {
                        MovieListState.Error(Throwable(SERVER_ERROR))
                    }
                )
            }

            override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
                MovieListState.Error(t)
            }

        }

        when (movieCategory) {
            MovieCategory.NOW_PLAYING -> {
                repository.getNowPlayingMovies(1, callback)
            }
            MovieCategory.TOP_RATED -> {
                repository.getTopRatedMovies(1, callback)
            }
            MovieCategory.POPULAR -> {
                repository.getPopularMovies(1, callback)
            }
        }
    }

    private fun addGenresNameToResponse(serverResponse: MoviesDTO?) {
        for (movie in serverResponse?.results!!) {
            val movieGenres = mutableListOf<String>()
            for (id in movie.genre_ids) {
                for (genre in genresList.genres) {
                    if (id == genre.id) {
                        movieGenres.add(genre.name)
                    }
                }
            }
            movie.genre_names = movieGenres
        }
    }

    private fun checkMovies(serverResponse: MoviesDTO): MoviesDTO {
        if (App.getAppPreferences()?.getBoolean(ADULT_CONTENT_SHOW_KEY, false) == true) {
            return adultFilterMovies(serverResponse)
        }
        return serverResponse
    }


    private fun adultFilterMovies(moviesToCheck: MoviesDTO): MoviesDTO {
        val checkedResults: MutableList<MoviesDTO.MoviePreview> = mutableListOf()
        for (movie in moviesToCheck.results) {
            if (!movie.adult) {
                checkedResults.add(movie)
            }
        }
        moviesToCheck.results = checkedResults
        return moviesToCheck
    }

    private fun getGenresList() {
        val callback = object : Callback<GenresDTO> {
            override fun onResponse(call: Call<GenresDTO>, response: Response<GenresDTO>) {
                val genresDTO: GenresDTO? = response.body()
                if (response.isSuccessful && genresDTO != null) {
                    genresList = genresDTO
                } else {
                    MovieListState.Error(Throwable(SERVER_ERROR))
                }
            }

            override fun onFailure(call: Call<GenresDTO>, t: Throwable) {
                // do nothing
            }
        }

        repository.getGenresList(callback)
    }
}