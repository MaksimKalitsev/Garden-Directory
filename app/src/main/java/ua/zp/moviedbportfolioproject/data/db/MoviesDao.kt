package ua.zp.moviedbportfolioproject.data.db


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(movieDbEntity: MovieDbEntity)

    @Query("SELECT * FROM `favorite_movie` LIMIT :size OFFSET :offset")
    fun getFavoriteMovies(size:Int, offset: Int): List<MovieDbEntity>

    @Update
    suspend fun updateFavoriteMovie(movieDbEntity: MovieDbEntity)

    @Query("SELECT * FROM favorite_movie WHERE id IN (:ids)")
     fun getMoviesByIds(ids: List<Int>): List<MovieDbEntity>

     @Delete
     suspend fun deleteFavoriteMovie(movieDbEntity: MovieDbEntity)
}