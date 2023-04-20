package com.privin.movies.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [GenreEntity::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun createGenre(): GenreDao

    companion object{

        fun createDb(context: Context): Database {
            return Room.databaseBuilder(
                context,
                Database::class.java,
                "GenreDb"
            ).build()
        }
    }
}