package com.privin.movies.domain

import com.privin.movies.data.Repo
import javax.inject.Inject

class GetAllGenres @Inject constructor(
    private val repo: Repo
) {

    suspend fun execute(): Map<Int, String> {
        val genres = repo.getGenre()
        val map = HashMap<Int, String>()
        genres.forEach {
            map[it.id] = it.name
        }
        return map
    }
}