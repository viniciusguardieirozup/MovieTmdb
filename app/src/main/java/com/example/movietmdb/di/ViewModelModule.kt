package com.example.movietmdb.di

import com.example.movietmdb.features.description.ui.Description2Activity
import com.example.movietmdb.features.description.ui.DescriptionViewModel
import org.koin.dsl.module.module

val viewModel = module{
    single{this}
    factory{ (viewModel : DescriptionViewModel) -> Description2Activity() }
}