package ua.zp.gardendirectory.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieData(
    val id: Int,
    val title: String?,
    val photo: String?,
    val description: String?
): Parcelable
