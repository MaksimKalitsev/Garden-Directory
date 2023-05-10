package ua.zp.gardendirectory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ua.zp.gardendirectory.data.models.MovieData
import ua.zp.gardendirectory.databinding.ItemRecyclerviewBinding

class MovieAdapter(
    diffCallback: DiffUtil.ItemCallback<MovieData>,
    private val navCallback: MovieHolder.NavCallback
) : PagingDataAdapter<MovieData, MovieAdapter.MovieHolder>(diffCallback) {


    class MovieHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val rvItem = binding.itemRecyclerView

        interface NavCallback {
            fun onItemRecyclerViewClicked(item: MovieData)
        }

        fun bind(data: MovieData) = with(binding) {
            ivPhoto.load(data.preview)
            tvName.text = data.title
            tvDescription.text = data.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerviewBinding.inflate(inflater, parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
            holder.rvItem.setOnClickListener {
                navCallback.onItemRecyclerViewClicked(movie)
            }
        }

    }
}