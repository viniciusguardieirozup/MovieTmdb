package com.example.movietmdb.pagination

import androidx.paging.PageKeyedDataSource
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DataSource(val repositoryRules: RepositoryRules, val id : Int) :
    PageKeyedDataSource<Int, MoviePresentation>() {

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MoviePresentation>
    ) {
        scope.launch {
            try {

                callback.onResult(
                    MoviePresentationMapper().convertListMovieService(
                        repositoryRules.getSimilar(id, 1).results, repositoryRules.getFavMovies()
                    ), null, 1
                )
            } catch (e: Exception) {

            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MoviePresentation>
    ) {
        val page = params.key
        scope.launch {
            try {

                callback.onResult(
                    MoviePresentationMapper().convertListMovieService(
                        repositoryRules.getSimilar(id, page + 1).results,
                        repositoryRules.getFavMovies()
                    ), page + 1
                )
            } catch (e: Exception) {

            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MoviePresentation>
    ) {
        val page = params.key
        scope.launch {
            try {

                callback.onResult(
                    MoviePresentationMapper().convertListMovieService(
                        repositoryRules.getSimilar(id, page - 1).results,
                        repositoryRules.getFavMovies()
                    ), page - 1
                )
            } catch (e: Exception) {

            }
        }
    }

}