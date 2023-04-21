package com.privin.movies.data.local

import androidx.room.*

@Dao
interface FavMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favMovieEntity: FavMovieEntity)

    @Query("select * from FavMovieEntity")
    fun getAllFavMovies(): List<FavMovieEntity>
    @Query("delete from FavMovieEntity where id=:id")
    fun removeFromFav(id: Long)
    @Query("select COUNT(id) from FavMovieEntity where id=:id")
    fun isFavMovie(id: Long): Int
}