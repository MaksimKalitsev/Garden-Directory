package ua.zp.moviedbportfolioproject.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ua.zp.moviedbportfolioproject.data.models.MovieData

@Entity(tableName = "favorite_movie",
indices = [
    Index("id", unique = true)
])
data class MovieDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val photo: String,
    val description: String,
    val isFavorite: Boolean
) {
    fun toMovieData(): MovieData = MovieData(
        id = id,
        title = title,
        photo = photo,
        description = description,
        isFavorite = isFavorite
    )
}