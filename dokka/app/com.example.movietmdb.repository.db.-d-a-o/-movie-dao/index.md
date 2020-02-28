[app](../../index.md) / [com.example.movietmdb.repository.db.DAO](../index.md) / [MovieDao](./index.md)

# MovieDao

`interface MovieDao`

### Functions

| [getAll](get-all.md) | `abstract suspend fun getAll(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`MovieData`](../../com.example.movietmdb.repository.db.entity/-movie-data/index.md)`>` |
| [insertMovie](insert-movie.md) | `abstract suspend fun insertMovie(movie: `[`MovieData`](../../com.example.movietmdb.repository.db.entity/-movie-data/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [removeMovie](remove-movie.md) | `abstract suspend fun removeMovie(movie: `[`MovieData`](../../com.example.movietmdb.repository.db.entity/-movie-data/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [searchMovie](search-movie.md) | `abstract suspend fun searchMovie(movieID: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`MovieData`](../../com.example.movietmdb.repository.db.entity/-movie-data/index.md) |

