[app](../../index.md) / [com.example.movietmdb.repository.retrofit](../index.md) / [MoviesAPI](index.md) / [searchMoviesByUser](./search-movies-by-user.md)

# searchMoviesByUser

`@GET("search/movie?") abstract suspend fun searchMoviesByUser(@Query("query") name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Query("page") number: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SearchResults`](../-search-results/index.md)