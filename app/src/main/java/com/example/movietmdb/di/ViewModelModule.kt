package com.example.movietmdb.di

import com.example.movietmdb.viewModel.BaseMovieViewModel
import com.example.movietmdb.features.description.viewmodel.DescriptionViewModel
import com.example.movietmdb.features.main.viewmodel.FavoritesViewModel
import com.example.movietmdb.features.main.viewmodel.GenreViewModel
import com.example.movietmdb.features.main.viewmodel.SearchViewModel
import com.example.movietmdb.recycler.viewmodel.GenreRecyclerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { DescriptionViewModel(moviesRepository = get()) }
    viewModel { BaseMovieViewModel() }
    viewModel { FavoritesViewModel(moviesRepository = get()) }
    viewModel { GenreViewModel(moviesRepository = get()) }
    viewModel { SearchViewModel(moviesRepository = get()) }
    viewModel { GenreRecyclerViewModel(moviesRepository = get()) }
}