package com.privin.movies.domain

import com.privin.movies.data.Repo
import javax.inject.Inject

class IsFavMovie @Inject constructor(
    private val repo: Repo
) {

    suspend fun execute(id: Long): Boolean{
        return repo.isFavMovie(id)
    }
}