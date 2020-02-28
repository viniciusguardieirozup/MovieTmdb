[app](../../index.md) / [com.example.movietmdb.features.description.viewmodel](../index.md) / [DescriptionViewModel](./index.md)

# DescriptionViewModel

`class DescriptionViewModel : `[`PaginationViewModel`](../../com.example.movietmdb.view-model/-pagination-view-model/index.md)

Example of a class comment.

### Parameters

`app` - : Example of a param comment.

### Constructors

| [&lt;init&gt;](-init-.md) | Example of a class comment.`DescriptionViewModel(moviesRepository: `[`MoviesRepository`](../../com.example.movietmdb.repository/-movies-repository/index.md)`)` |

### Properties

| [favorite](favorite.md) | `val favorite: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [moviesRepository](movies-repository.md) | `val moviesRepository: `[`MoviesRepository`](../../com.example.movietmdb.repository/-movies-repository/index.md) |
| [url](url.md) | `val url: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |

### Functions

| [accessRepositoryMapResult](access-repository-map-result.md) | `suspend fun accessRepositoryMapResult(): `[`SearchResults`](../../com.example.movietmdb.repository.retrofit/-search-results/index.md) |
| [getSimilar](get-similar.md) | `fun getSimilar(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setFavorite](set-favorite.md) | `fun setFavorite(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [startViewModel](start-view-model.md) | `fun startViewModel(movie: `[`MoviePresentation`](../../com.example.movietmdb.recycler.data/-movie-presentation/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

