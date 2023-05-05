package ua.zp.gardendirectory.data.network.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ua.zp.gardendirectory.data.models.PlantData

@Parcelize
data class PlantsResponse(
    val data: List<PlantListResponse>,
    val meta: Meta
): Parcelable{

}
@Parcelize
data class PlantListResponse(
    val id: Int,
    val common_name: String?,
    val slug: String?,
    val scientific_name: String?,
    val year: Int,
    val bibliography: String?,
    val author: String?,
    val status: String?,
    val rank: String?,
    val family_common_name: String? = null,
    val genus_id: Int,
    val image_url: String?,
    val synonyms: List<String>,
    val genus: String?,
    val family: String?,
) : Parcelable {
    fun toPlantData(): PlantData =
        PlantData(
            id = id,
            name = common_name,
            description = bibliography,
            photo = image_url
        )
}
@Parcelize
data class Links(
    val self: String,
    val plant: String,
    val genus: String
) : Parcelable

@Parcelize
data class Meta(
    val total: Int
): Parcelable


