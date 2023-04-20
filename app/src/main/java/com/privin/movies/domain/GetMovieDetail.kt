package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.model.MovieDetail
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val repo: Repo,
) {

    suspend fun execute(id: Long): MovieDetail {
        val response = repo.getMovieDetail(id)
        return response.toMovieDetail()
    }
}