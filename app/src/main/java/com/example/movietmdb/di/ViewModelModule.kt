package com.example.movietmdb.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.BuildConfig
import com.example.movietmdb.features.description.viewmodel.DescriptionViewModel
import com.example.movietmdb.features.main.viewmodel.FavoritesViewModel
import com.example.movietmdb.features.main.viewmodel.GenreViewModel
import com.example.movietmdb.features.main.viewmodel.SearchFragmentViewModel
import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.adapter.DescriptionAdapter
import com.example.movietmdb.recycler.adapter.GenreAdapter
import com.example.movietmdb.recycler.viewmodel.GenreRecyclerViewModel
import com.example.movietmdb.recycler.viewmodel.HeaderViewModel
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.AppDatabase
import com.example.movietmdb.repository.db.DAO.MovieDao
import com.example.movietmdb.repository.retrofit.MoviesAPI
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelsModule = module {
    viewModel {DescriptionViewModel(moviesRepository = get())}
    viewModel { BaseMovieViewModel() }
    viewModel { FavoritesViewModel(moviesRepository = get()) }
    viewModel { GenreViewModel(moviesRepository = get()) }
    viewModel { SearchFragmentViewModel(moviesRepository = get()) }
    viewModel {GenreRecyclerViewModel(moviesRepository = get())}
    viewModel {HeaderViewModel(moviesRepository = get())}

}

val adapter = module {
    factory { CustomAdapter() }
    factory { GenreAdapter() }
    factory { DescriptionAdapter() }
}

val request = module{
    single { RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) }
}


val apiModule = module {
    fun providesMoviesApi(retrofit: Retrofit): MoviesAPI {
        return retrofit.create(MoviesAPI::class.java)
    }
    single { providesMoviesApi(retrofit = get()) }
}

val netModule = module {
    single {
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
    }
    single(named("INTERCEPTOR")) {
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url: HttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_KEY)
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                return chain.proceed(request)
            }

        }
    }
    single {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>(named("INTERCEPTOR")))
            .build()
    }

    single {
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()).client(get<OkHttpClient>()).build()
    }
}

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "fav_movies").build()
    }

    fun provideDAO(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDAO(database = get()) }
}

val repositoryModule = module {
    fun provideRepositoryRules(api: MoviesAPI, dao: MovieDao): MoviesRepository {
        return MoviesRepository(api, dao)
    }
    single {
        provideRepositoryRules(
            api = get(),
            dao = get()
        )
    }
}