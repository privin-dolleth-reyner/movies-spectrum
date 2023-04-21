package com.privin.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favMovieEntity: FavMovieEntity)

    @Query("select * from FavMovieEntity")
    fun getAllFavMovies(): List<FavMovieEntity>
}