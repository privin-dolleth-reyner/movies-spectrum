package com.privin.movies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity (
    @PrimaryKey
    var id: Int,
    var name: String
)