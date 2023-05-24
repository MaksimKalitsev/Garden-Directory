package ua.zp.moviedbportfolioproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ua.zp.moviedbportfolioproject.R
import ua.zp.moviedbportfolioproject.data.models.MovieData
import ua.zp.moviedbportfolioproject.databinding.ItemRecyclerviewBinding

class MovieAdapter(
    diffCallback: DiffUtil.ItemCallback<MovieData>,
    private val navCallback: MovieHolder.NavCallback
) : PagingDataAdapter<MovieData, MovieAdapter.MovieHolder>(diffCallback) {

    class MovieHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val rvItem = binding.itemRecyclerView
        val ivFavorite = binding.ivFavorite

        interface NavCallback {
            fun onItemRecyclerViewClicked(item: MovieData)
            fun addFavoriteMovie(movie: MovieData)
            fun removeFavoriteMovie(movie: MovieData)
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

            val context = holder.ivFavorite.context
            val colorRes = if (movie.isFavorite) R.color.iv_favorite else R.color.black
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context, colorRes))

            holder.rvItem.setOnClickListener {
                navCallback.onItemRecyclerViewClicked(movie)
            }

            holder.ivFavorite.setOnClickListener {
                val isFavorite = movie.isFavorite
                movie.isFavorite = !isFavorite
                if (movie.isFavorite){
                    navCallback.addFavoriteMovie(movie)
                } else {
                    navCallback.removeFavoriteMovie(movie)
                }
                val updatedColorRes = if (movie.isFavorite) R.color.iv_favorite else R.color.black
                holder.ivFavorite.setColorFilter(ContextCompat.getColor(context, updatedColorRes))
            }
        }
    }
}
