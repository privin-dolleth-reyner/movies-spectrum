package com.privin.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genreList: List<GenreEntity>)

    @Query("select * from GenreEntity")
    fun getAll(): List<GenreEntity>
}