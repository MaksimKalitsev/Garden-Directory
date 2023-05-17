package ua.zp.moviedbportfolioproject.ui

import androidx.recyclerview.widget.DiffUtil
import ua.zp.moviedbportfolioproject.data.models.MovieData

class DiffCallback(private val oldList: List<MovieData>, private val newList: List<MovieData>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPhoto = oldList[oldItemPosition]
        val newPhoto = newList[newItemPosition]
        return oldPhoto.id == newPhoto.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPhoto = oldList[oldItemPosition]
        val newPhoto = newList[newItemPosition]
        return oldPhoto == newPhoto
    }

}