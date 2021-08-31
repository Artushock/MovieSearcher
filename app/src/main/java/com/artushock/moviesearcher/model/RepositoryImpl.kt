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
        data.add(Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10.0f, MovieCategory.POPULAR))
        data.add(Movie("Крёстный отец", MovieGenre.ACTION, 9.8f, MovieCategory.POPULAR))
        data.add(Movie("Начало", MovieGenre.ACTION, 9.2f, MovieCategory.POPULAR))
        data.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 10f, MovieCategory.POPULAR))
        data.add(Movie("Форрест Гамп", MovieGenre.DRAMA, 9.6f, MovieCategory.POPULAR))
        data.add(Movie("Матрица", MovieGenre.ACTION, 9.7f, MovieCategory.POPULAR))
        data.add(
            Movie(
                "Пролетая над гнездом кукушки",
                MovieGenre.ACTION,
                8.4f,
                MovieCategory.POPULAR
            )
        )
        data.add(Movie("Город Бога", MovieGenre.ACTION, 9f, MovieCategory.POPULAR))
        data.add(Movie("Молчание ягнят", MovieGenre.DRAMA, 10f, MovieCategory.POPULAR))
        data.add(Movie("Леон", MovieGenre.ACTION, 7.4f, MovieCategory.POPULAR))
        data.add(Movie("Американская история Икс", MovieGenre.ACTION, 9f, MovieCategory.POPULAR))
        data.add(Movie("Касабланка", MovieGenre.ACTION, 9.2f, MovieCategory.POPULAR))
        data.add(Movie("Однажды на Диком Западеа", MovieGenre.WESTERN, 8f, MovieCategory.POPULAR))
        data.add(Movie("Чужой", MovieGenre.ACTION, 8.1f, MovieCategory.POPULAR))
        data.add(Movie("Великий диктатор", MovieGenre.COMEDY, 9.1f, MovieCategory.POPULAR))
        data.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9.1f, MovieCategory.POPULAR))

        data.add(Movie("Поступь хаоса", MovieGenre.ACTION, 6.3f, MovieCategory.NEW))
        data.add(Movie("Пила: Спираль", MovieGenre.HORROR, 6.3f, MovieCategory.NEW))
        data.add(Movie("Трафик", MovieGenre.THRILLER, 6.3f, MovieCategory.NEW))
        data.add(Movie("Гнев человеческий", MovieGenre.ACTION, 6.3f, MovieCategory.NEW))
        data.add(Movie("Ага", MovieGenre.ACTION, 6.3f, MovieCategory.NEW))
        data.add(Movie("Райская бухтаа", MovieGenre.THRILLER, 6.3f, MovieCategory.NEW))
        data.add(Movie("Хребет Дьявола", MovieGenre.HORROR, 6.3f, MovieCategory.NEW))
        data.add(Movie("Тихое место 2", MovieGenre.HORROR, 6.3f, MovieCategory.NEW))
        data.add(Movie("Асоциальная сеть", MovieGenre.THRILLER, 6.3f, MovieCategory.NEW))
        data.add(Movie("Лес самоубийц", MovieGenre.HORROR, 6.3f, MovieCategory.NEW))
        data.add(Movie("Мортал Комбат", MovieGenre.ACTION, 6.3f, MovieCategory.NEW))

        return data
    }
}