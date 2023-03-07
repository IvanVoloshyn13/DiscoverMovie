package com.example.discovermovie.data.localeDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @SerializedName("id")
    val movieId: Int,
    val original_title: String,
    val poster_path: String,
    val status: String,
    val release_date: String
)