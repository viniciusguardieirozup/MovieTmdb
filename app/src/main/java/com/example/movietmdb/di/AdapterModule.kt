package com.example.movietmdb.di

import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.adapter.DescriptionAdapter
import com.example.movietmdb.recycler.adapter.GenreAdapter
import org.koin.dsl.module


val adapter = module {
    factory { CustomAdapter() }
    factory { GenreAdapter() }
    factory { DescriptionAdapter() }
}