package com.privin.movies.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [GenreEntity::class, FavMovieEntity::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun createGenre(): GenreDao
    abstract fun createFavMovie(): FavMovieDao
    companion object{

        fun createDb(context: Context): Database {
            return Room.databaseBuilder(
                context,
                Database::class.java,
                "Spectrum"
            ).build()
        }
    }
}