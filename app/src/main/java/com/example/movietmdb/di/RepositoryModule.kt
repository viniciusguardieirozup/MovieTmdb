package com.example.movietmdb.di

import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.DAO.MovieDao
import com.example.movietmdb.repository.retrofit.MoviesAPI
import org.koin.dsl.module

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