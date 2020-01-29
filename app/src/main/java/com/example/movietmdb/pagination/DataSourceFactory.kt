package com.example.movietmdb.pagination

import androidx.paging.DataSource
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules

class DataSourceFactory(val repositoryRules: RepositoryRules,val id : Int) : DataSource.Factory<Int, MoviePresentation>(){
    override fun create(): DataSource<Int, MoviePresentation> {
        return DataSource(repositoryRules, id)
    }

}