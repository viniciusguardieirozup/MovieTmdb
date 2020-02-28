[app](../../index.md) / [com.example.movietmdb.viewModel](../index.md) / [BaseMovieViewModel](./index.md)

# BaseMovieViewModel

`open class BaseMovieViewModel : ViewModel`

### Constructors

| [&lt;init&gt;](-init-.md) | `BaseMovieViewModel()` |

### Properties

| [job](job.md) | `var job: Job` |
| [loading](loading.md) | `var loading: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [movie](movie.md) | `lateinit var movie: `[`MoviePresentation`](../../com.example.movietmdb.recycler.data/-movie-presentation/index.md) |
| [moviesLiveData](movies-live-data.md) | `val moviesLiveData: MutableLiveData<`[`ViewState`](../-view-state/index.md)`>` |

### Functions

| [load](load.md) | `fun load(block: suspend () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| [FavoritesViewModel](../../com.example.movietmdb.features.main.viewmodel/-favorites-view-model/index.md) | `class FavoritesViewModel : `[`BaseMovieViewModel`](./index.md) |
| [GenreViewModel](../../com.example.movietmdb.features.main.viewmodel/-genre-view-model/index.md) | `class GenreViewModel : `[`BaseMovieViewModel`](./index.md) |
| [PaginationViewModel](../-pagination-view-model/index.md) | `abstract class PaginationViewModel : `[`BaseMovieViewModel`](./index.md) |

