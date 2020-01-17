package com.example.movietmdb.di

import android.app.Application
import androidx.room.Room
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.features.description.ui.DescriptionViewModel
import com.example.movietmdb.features.main.viewmodel.FavoritesViewModel
import com.example.movietmdb.features.main.viewmodel.GenreViewModel
import com.example.movietmdb.features.main.viewmodel.SearchFragmentViewModel
import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.adapter.GenreAdapter
import com.example.movietmdb.recycler.viewmodel.GenreRecyclerViewModel
import com.example.movietmdb.repository.RepositoryRules
import com.example.movietmdb.repository.db.AppDatabase
import com.example.movietmdb.repository.db.DAO.MovieDao
import com.example.movietmdb.repository.retrofit.MoviesAPI
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelsModule = module {
    viewModel { DescriptionViewModel(get()) }
    viewModel { BaseMovieViewModel() }
    viewModel { FavoritesViewModel(get()) }
    viewModel { GenreViewModel(get()) }
    viewModel { SearchFragmentViewModel(get()) }
    viewModel {
        GenreRecyclerViewModel(
            get()
        )
    }

}

val adapter = module {
    factory { CustomAdapter() }
    factory { GenreAdapter() }
}


val apiModule = module {
    fun providesMoviesApi(retrofit: Retrofit): MoviesAPI {
        return retrofit.create(MoviesAPI::class.java)
    }
    single { providesMoviesApi(get()) }
}

val netModule = module {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    single { provideRetrofit() }
}

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "fav_movies").build()
    }

    fun provideDAO(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDAO(get()) }
}

val repositoryModule = module {
    fun provideRepositoryRules(api: MoviesAPI, dao: MovieDao): RepositoryRules {
        return RepositoryRules(api, dao)
    }
    single { provideRepositoryRules(get(), get()) }
}