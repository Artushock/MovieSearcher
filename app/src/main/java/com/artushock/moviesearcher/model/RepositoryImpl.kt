package com.artushock.moviesearcher.model

class RepositoryImpl : Repository {

    override fun getMovieListFromLocalStorage(): ArrayList<Movie> {
        return getTestData()
    }

    override fun getMovieListFromRemoteStorage(): ArrayList<Movie> {
        return getTestData()
    }

    override fun getMoviesByName(text: String): ArrayList<Movie> {
        val foundMovies: ArrayList<Movie> = arrayListOf()
        val movies = getTestData()

        for (m in movies){
            if (m.name
                    .lowercase()
                    .contains(text.lowercase())){
                foundMovies.add(m)
            }
        }
        return foundMovies
    }

    private fun getTestData(): ArrayList<Movie>  =
        arrayListOf(
            Movie(
                "Побег из Шоушенка",
                mainGenre = MovieGenre.DRAMA,
                rating = 10.0f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Крёстный отец",
                mainGenre = MovieGenre.ACTION,
                rating = 9.8f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Начало",
                mainGenre = MovieGenre.ACTION,
                rating = 9.2f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Москва слехам не верит",
                mainGenre = MovieGenre.ACTION,
                rating = 10f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Форрест Гамп",
                mainGenre = MovieGenre.DRAMA,
                rating = 9.6f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Матрица",
                mainGenre = MovieGenre.ACTION,
                rating = 9.7f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Город Бога",
                mainGenre = MovieGenre.ACTION,
                rating = 9f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Молчание ягнят",
                mainGenre = MovieGenre.DRAMA,
                rating = 10f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Леон",
                mainGenre = MovieGenre.ACTION,
                rating = 7.4f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Американская история Икс",
                mainGenre = MovieGenre.ACTION,
                rating = 9f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Касабланка",
                mainGenre = MovieGenre.ACTION,
                rating = 9.2f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Однажды на Диком Западеа",
                mainGenre = MovieGenre.WESTERN,
                rating = 8f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Чужой",
                mainGenre = MovieGenre.ACTION,
                rating = 8.1f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Великий диктатор",
                mainGenre = MovieGenre.COMEDY,
                rating = 9.1f,
                category = MovieCategory.POPULAR
            ),
            Movie(
                "Москва слехам не верит",
                mainGenre = MovieGenre.ACTION,
                rating = 9.1f,
                category = MovieCategory.POPULAR
            ),

            Movie(
                "Пила: Спираль",
                mainGenre = MovieGenre.HORROR,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Трафик",
                mainGenre = MovieGenre.THRILLER,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Гнев человеческий",
                mainGenre = MovieGenre.ACTION,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Ага",
                mainGenre = MovieGenre.ACTION,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Райская бухтаа",
                mainGenre = MovieGenre.THRILLER,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Хребет Дьявола",
                mainGenre = MovieGenre.HORROR,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Тихое место 2",
                mainGenre = MovieGenre.HORROR,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Асоциальная сеть",
                mainGenre = MovieGenre.THRILLER,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Лес самоубийц",
                mainGenre = MovieGenre.HORROR,
                rating = 6.3f,
                category = MovieCategory.NEW
            ),
            Movie(
                "Мортал Комбат",
                mainGenre = MovieGenre.ACTION,
                rating = 6.3f,
                category = MovieCategory.NEW
            )
        )
}