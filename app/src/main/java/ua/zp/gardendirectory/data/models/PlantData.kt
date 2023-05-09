package ua.zp.gardendirectory.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantData(
    val id: Int,
    val name: String?,
    val photo: String?,
    val description: String?
): Parcelable
