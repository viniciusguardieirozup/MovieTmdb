[app](../../index.md) / [com.example.movietmdb.viewModel](../index.md) / [PaginationViewModel](./index.md)

# PaginationViewModel

`abstract class PaginationViewModel : `[`BaseMovieViewModel`](../-base-movie-view-model/index.md)

### Constructors

| [&lt;init&gt;](-init-.md) | `PaginationViewModel()` |

### Properties

| [lastPage](last-page.md) | `var lastPage: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [page](page.md) | `var page: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Functions

| [checkLastPage](check-last-page.md) | `fun checkLastPage(moviesResults: `[`SearchResults`](../../com.example.movietmdb.repository.retrofit/-search-results/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [noMorePageAvailable](no-more-page-available.md) | `fun noMorePageAvailable(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [noMoviesAvailable](no-movies-available.md) | `fun noMoviesAvailable(moviesResults: `[`SearchResults`](../../com.example.movietmdb.repository.retrofit/-search-results/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [returnLastPage](return-last-page.md) | `fun returnLastPage(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [returnPage](return-page.md) | `fun returnPage(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Inheritors

| [DescriptionViewModel](../../com.example.movietmdb.features.description.viewmodel/-description-view-model/index.md) | Example of a class comment.`class DescriptionViewModel : `[`PaginationViewModel`](./index.md) |
| [GenreRecyclerViewModel](../../com.example.movietmdb.recycler.viewmodel/-genre-recycler-view-model/index.md) | `class GenreRecyclerViewModel : `[`PaginationViewModel`](./index.md) |
| [SearchViewModel](../../com.example.movietmdb.features.main.viewmodel/-search-view-model/index.md) | `class SearchViewModel : `[`PaginationViewModel`](./index.md) |

