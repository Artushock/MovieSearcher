package com.artushock.moviesearcher.model

class RepositoryImpl : Repository {

    override fun getMovieListFromLocalStorage(): ArrayList<Movie> {
        return getTestData()
    }


    override fun getMovieListFromRemoteStorage(): ArrayList<Movie> {
        return getTestData()
    }

    private fun getTestData(): ArrayList<Movie> {
        val data = ArrayList<Movie>()
        data.add(Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10f))
        data.add(Movie("Крёстный отец", MovieGenre.ACTION, 10f))
        data.add(Movie("Начало", MovieGenre.ACTION, 9f))
        data.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9f))
        data.add(Movie("Форрест Гамп", MovieGenre.DRAMA, 10f))
        data.add(Movie("Матрица", MovieGenre.ACTION, 10f))
        data.add(Movie("Пролетая над гнездом кукушки", MovieGenre.ACTION, 9f))
        data.add(Movie("Город Бога", MovieGenre.ACTION, 9f))
        data.add(Movie("Молчание ягнят", MovieGenre.DRAMA, 10f))
        data.add(Movie("Леон", MovieGenre.ACTION, 10f))
        data.add(Movie("Американская история Икс", MovieGenre.ACTION, 9f))
        data.add(Movie("Касабланка", MovieGenre.ACTION, 9f))
        data.add(Movie("Однажды на Диком Западеа", MovieGenre.WESTERN, 8f))
        data.add(Movie("Чужой", MovieGenre.ACTION, 8f))
        data.add(Movie("Великий диктатор", MovieGenre.COMEDY, 9f))
        data.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9f))
        return data
    }
}