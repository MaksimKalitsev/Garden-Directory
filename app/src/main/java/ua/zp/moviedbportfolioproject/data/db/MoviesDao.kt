package ua.zp.moviedbportfolioproject.data.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {
    @Insert
    suspend fun addFavoriteMovie(movieDbEntity: MovieDbEntity)

    @Query("SELECT * FROM `favorite_movie` WHERE id = :movieID")
     fun getFavoriteMovies(movieID:Int): Flow<MovieDbEntity>
}