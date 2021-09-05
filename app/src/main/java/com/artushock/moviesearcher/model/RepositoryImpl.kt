package com.artushock.moviesearcher.model

class RepositoryImpl : Repository {

    override fun getMovieListFromLocalStorage(): ArrayList<Movie> {
        return getTestData()
    }

    override fun getMovieListFromRemoteStorage(): ArrayList<Movie> {
        return getTestData()
    }

    private fun getTestData(): ArrayList<Movie> {
        return arrayListOf(
            Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10.0f, MovieCategory.POPULAR),
            Movie("Крёстный отец", MovieGenre.ACTION, 9.8f, MovieCategory.POPULAR),
            Movie("Начало", MovieGenre.ACTION, 9.2f, MovieCategory.POPULAR),
            Movie("Москва слехам не верит", MovieGenre.ACTION, 10f, MovieCategory.POPULAR),
            Movie("Форрест Гамп", MovieGenre.DRAMA, 9.6f, MovieCategory.POPULAR),
            Movie("Матрица", MovieGenre.ACTION, 9.7f, MovieCategory.POPULAR),
            Movie("Город Бога", MovieGenre.ACTION, 9f, MovieCategory.POPULAR),
            Movie("Молчание ягнят", MovieGenre.DRAMA, 10f, MovieCategory.POPULAR),
            Movie("Леон", MovieGenre.ACTION, 7.4f, MovieCategory.POPULAR),
            Movie("Американская история Икс", MovieGenre.ACTION, 9f, MovieCategory.POPULAR),
            Movie("Касабланка", MovieGenre.ACTION, 9.2f, MovieCategory.POPULAR),
            Movie("Однажды на Диком Западеа", MovieGenre.WESTERN, 8f, MovieCategory.POPULAR),
            Movie("Чужой", MovieGenre.ACTION, 8.1f, MovieCategory.POPULAR),
            Movie("Великий диктатор", MovieGenre.COMEDY, 9.1f, MovieCategory.POPULAR),
            Movie("Москва слехам не верит", MovieGenre.ACTION, 9.1f, MovieCategory.POPULAR),

            Movie("Пила: Спираль", MovieGenre.HORROR, 6.3f, MovieCategory.NEW),
            Movie("Трафик", MovieGenre.THRILLER, 6.3f, MovieCategory.NEW),
            Movie("Гнев человеческий", MovieGenre.ACTION, 6.3f, MovieCategory.NEW),
            Movie("Ага", MovieGenre.ACTION, 6.3f, MovieCategory.NEW),
            Movie("Райская бухтаа", MovieGenre.THRILLER, 6.3f, MovieCategory.NEW),
            Movie("Хребет Дьявола", MovieGenre.HORROR, 6.3f, MovieCategory.NEW),
            Movie("Тихое место 2", MovieGenre.HORROR, 6.3f, MovieCategory.NEW),
            Movie("Асоциальная сеть", MovieGenre.THRILLER, 6.3f, MovieCategory.NEW),
            Movie("Лес самоубийц", MovieGenre.HORROR, 6.3f, MovieCategory.NEW),
            Movie("Мортал Комбат", MovieGenre.ACTION, 6.3f, MovieCategory.NEW)
        )
    }
}