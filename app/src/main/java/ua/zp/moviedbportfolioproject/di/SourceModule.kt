package ua.zp.moviedbportfolioproject.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.zp.moviedbportfolioproject.repository.IMoviesRepository
import ua.zp.moviedbportfolioproject.repository.MoviesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    abstract fun bindIMoviesRepository(
       repository: MoviesRepository
    ): IMoviesRepository
}