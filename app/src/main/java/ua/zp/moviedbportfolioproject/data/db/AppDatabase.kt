package ua.zp.moviedbportfolioproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 2, entities = [MovieDbEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao
}