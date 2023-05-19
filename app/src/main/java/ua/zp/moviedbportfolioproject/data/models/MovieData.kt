package ua.zp.moviedbportfolioproject.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ua.zp.moviedbportfolioproject.Confiq.BASE_URL_IMAGES
import ua.zp.moviedbportfolioproject.data.db.MovieDbEntity

@Parcelize
data class MovieData(
    val id: Int,
    val title: String?,
    val photo: String?,
    val description: String?
): Parcelable {
    val preview: String?
        get() = photo?.let {
            "$BASE_URL_IMAGES/w500/$photo"
        }
    val originalSize: String?
        get() = photo?.let {
            "$BASE_URL_IMAGES/original/$photo"
        }
    fun toDbEntity(): MovieDbEntity {
        return MovieDbEntity(
            id = this.id,
            title = this.title.orEmpty(),
            photo = this.photo.orEmpty(),
            description = this.description.orEmpty()
        )
    }
}
