package com.example.movietmdb.repository.retrofit

import com.google.gson.annotations.SerializedName

data class GenresList(@SerializedName("genres") val genres : ArrayList<Genres>)