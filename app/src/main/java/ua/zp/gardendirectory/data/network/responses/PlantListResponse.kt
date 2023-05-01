package ua.zp.gardendirectory.data.network.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantListResponse(
    val id: Int,
    val common_name: String,
    val slug: String,
    val scientific_name: String,
    val year: Int,
    val bibliography: String,
    val author: String,
    val status: String,
    val rank: String,
    val family_common_name: String,
    val genus_id: Int,
    val image_url: String,
    val synonyms: List<String>,
    val genus: String,
    val family: String,
    val links: Links
    ) : Parcelable {
        @Parcelize
            data class Links(
                    val self: String,
                    val plant: String,
                    val genus: String
            ): Parcelable
    }

