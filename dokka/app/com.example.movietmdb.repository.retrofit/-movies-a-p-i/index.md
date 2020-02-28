[app](../../index.md) / [com.example.movietmdb.repository.retrofit](../index.md) / [MoviesAPI](./index.md)

# MoviesAPI

`interface MoviesAPI`

### Functions

| [getGenres](get-genres.md) | `abstract suspend fun getGenres(): `[`GenresList`](../-genres-list/index.md) |
| [getMoviesByGenres](get-movies-by-genres.md) | `abstract suspend fun getMoviesByGenres(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, page: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SearchResults`](../-search-results/index.md) |
| [getSimilar](get-similar.md) | `abstract suspend fun getSimilar(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, page: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SearchResults`](../-search-results/index.md) |
| [searchMoviesByUser](search-movies-by-user.md) | `abstract suspend fun searchMoviesByUser(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, number: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SearchResults`](../-search-results/index.md) |

