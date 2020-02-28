[app](../../index.md) / [com.example.movietmdb.repository](../index.md) / [MoviesRepository](./index.md)

# MoviesRepository

`class MoviesRepository`

### Constructors

| [&lt;init&gt;](-init-.md) | `MoviesRepository(moviesAPI: `[`MoviesAPI`](../../com.example.movietmdb.repository.retrofit/-movies-a-p-i/index.md)`, movieDAO: `[`MovieDao`](../../com.example.movietmdb.repository.db.-d-a-o/-movie-dao/index.md)`)` |

### Functions

| [getAMovie](get-a-movie.md) | `suspend fun getAMovie(movieId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`MovieData`](../../com.example.movietmdb.repository.db.entity/-movie-data/index.md) |
| [getFavMovies](get-fav-movies.md) | `suspend fun getFavMovies(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`MovieData`](../../com.example.movietmdb.repository.db.entity/-movie-data/index.md)`>` |
| [getGenres](get-genres.md) | `suspend fun getGenres(): `[`GenresList`](../../com.example.movietmdb.repository.retrofit/-genres-list/index.md) |
| [getMovies](get-movies.md) | `suspend fun getMovies(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, page: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SearchResults`](../../com.example.movietmdb.repository.retrofit/-search-results/index.md) |
| [getMoviesByGenres](get-movies-by-genres.md) | `suspend fun getMoviesByGenres(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, page: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SearchResults`](../../com.example.movietmdb.repository.retrofit/-search-results/index.md) |
| [getSimilar](get-similar.md) | `suspend fun getSimilar(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, page: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SearchResults`](../../com.example.movietmdb.repository.retrofit/-search-results/index.md) |
| [insertMovie](insert-movie.md) | `suspend fun insertMovie(movie: `[`MovieData`](../../com.example.movietmdb.repository.db.entity/-movie-data/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [removeMovie](remove-movie.md) | `suspend fun removeMovie(movie: `[`MovieData`](../../com.example.movietmdb.repository.db.entity/-movie-data/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

