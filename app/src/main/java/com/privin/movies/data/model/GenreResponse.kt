package com.privin.movies.data.model

data class GenreResponse(
    var genres: List<Genre>
)

data class Genre(
    var id: Int,
    var name: String,
)