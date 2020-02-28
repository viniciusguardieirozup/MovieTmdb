[app](../../index.md) / [com.example.movietmdb.repository.retrofit](../index.md) / [MoviesAPI](index.md) / [getSimilar](./get-similar.md)

# getSimilar

`@GET("movie/{movie_id}/similar?") abstract suspend fun getSimilar(@Path("movie_id") id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @Query("page") page: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SearchResults`](../-search-results/index.md)