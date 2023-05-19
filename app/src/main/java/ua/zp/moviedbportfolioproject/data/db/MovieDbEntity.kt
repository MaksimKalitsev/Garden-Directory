package ua.zp.moviedbportfolioproject.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.zp.moviedbportfolioproject.data.models.MovieData

@Entity(tableName = "favorite_movie")
data class MovieDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val photo: String,
    val description: String
) {
    fun toMovieData(): MovieData = MovieData(
        id = id,
        title = title,
        photo = photo,
        description = description
    )
}