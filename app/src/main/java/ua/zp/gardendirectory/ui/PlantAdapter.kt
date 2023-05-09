package ua.zp.gardendirectory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ua.zp.gardendirectory.data.models.MovieData
import ua.zp.gardendirectory.databinding.ItemRecyclerviewBinding

class PlantAdapter(
    diffCallback: DiffUtil.ItemCallback<MovieData>,
    private val navCallback: PlantHolder.NavCallback
) : PagingDataAdapter<MovieData, PlantAdapter.PlantHolder>(diffCallback) {


    class PlantHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val rvItem = binding.itemRecyclerView

        interface NavCallback {
            fun onItemRecyclerViewClicked(item: MovieData)
        }

        fun bind(data: MovieData) = with(binding) {
            ivPhoto.load(data.photo)
            tvName.text = data.title
            tvDescription.text = data.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerviewBinding.inflate(inflater, parent, false)
        return PlantHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        getItem(position)?.let { plant ->
            holder.bind(plant)
            holder.rvItem.setOnClickListener {
                navCallback.onItemRecyclerViewClicked(plant)
            }
        }

    }
}