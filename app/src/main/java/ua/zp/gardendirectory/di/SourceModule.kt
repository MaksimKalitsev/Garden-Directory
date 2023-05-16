package ua.zp.gardendirectory.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.zp.gardendirectory.data.network.Api
import ua.zp.gardendirectory.repository.IMoviesRepository
import ua.zp.gardendirectory.repository.MoviesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    abstract fun bindIMoviesRepository(
       repository: MoviesRepository
    ): IMoviesRepository
}