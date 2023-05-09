package ua.zp.gardendirectory.data.network.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import ua.zp.gardendirectory.data.models.MovieData
@Parcelize
data class MovieResult(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
): Parcelable

@Parcelize
data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: String? = null,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
): Parcelable {
    fun toMovieData(): MovieData =
        MovieData(
            id = id,
            title = original_title,
            photo = poster_path,
            description = overview
        )
}
@Parcelize
data class Genre(
    val id: Int,
    val name: String
): Parcelable
@Parcelize
data class ProductionCompany(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String
): Parcelable
@Parcelize
data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
): Parcelable
@Parcelize
data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
):Parcelable


