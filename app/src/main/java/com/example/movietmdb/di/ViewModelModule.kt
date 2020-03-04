package com.example.movietmdb.di

import com.example.movietmdb.presentation.viewmodel.BaseMovieViewModel
import com.example.movietmdb.presentation.viewmodel.DescriptionViewModel
import com.example.movietmdb.presentation.viewmodel.FavoritesViewModel
import com.example.movietmdb.presentation.viewmodel.GenreViewModel
import com.example.movietmdb.presentation.viewmodel.SearchViewModel
import com.example.movietmdb.presentation.viewmodel.GenreRecyclerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        DescriptionViewModel(
            moviesRepository = get()
        )
    }
    viewModel { BaseMovieViewModel() }
    viewModel {
        FavoritesViewModel(
            moviesRepository = get()
        )
    }
    viewModel {
        GenreViewModel(
            moviesRepository = get()
        )
    }
    viewModel {
        SearchViewModel(
            moviesRepository = get()
        )
    }
    viewModel {
        GenreRecyclerViewModel(
            moviesRepository = get()
        )
    }
}