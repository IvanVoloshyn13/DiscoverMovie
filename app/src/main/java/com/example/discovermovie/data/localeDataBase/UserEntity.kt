package com.example.discovermovie.data.localeDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @SerializedName("id")
    val userId: Int,
    val include_adult: Boolean,
    val name: String,
    val username: String
)
