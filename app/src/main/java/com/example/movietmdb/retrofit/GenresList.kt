package com.example.movietmdb.retrofit

import com.google.gson.annotations.SerializedName

data class GenresList(@SerializedName("genres") val genres : ArrayList<Genres>)