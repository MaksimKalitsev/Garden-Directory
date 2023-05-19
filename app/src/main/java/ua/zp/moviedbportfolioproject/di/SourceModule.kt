package ua.zp.moviedbportfolioproject.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.zp.moviedbportfolioproject.data.db.AppDatabase
import ua.zp.moviedbportfolioproject.data.db.MoviesDao
import ua.zp.moviedbportfolioproject.repository.IMoviesRepository
import ua.zp.moviedbportfolioproject.repository.MoviesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    abstract fun bindIMoviesRepository(
        repository: MoviesRepository
    ): IMoviesRepository

}