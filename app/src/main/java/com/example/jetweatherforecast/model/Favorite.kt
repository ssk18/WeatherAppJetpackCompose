package com.example.jetweatherforecast.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "fav_tbl")
data class Favorite(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "country")
    val country: String
)
