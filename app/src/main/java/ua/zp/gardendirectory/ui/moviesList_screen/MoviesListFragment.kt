package ua.zp.gardendirectory.ui.moviesList_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.zp.gardendirectory.MovieType
import ua.zp.gardendirectory.R
import ua.zp.gardendirectory.data.models.MovieData
import ua.zp.gardendirectory.databinding.FragmentMoviesListBinding
import ua.zp.gardendirectory.ui.MovieAdapter
import ua.zp.gardendirectory.ui.details_screen.DetailsFragmentArgs
import ua.zp.gardendirectory.ui.view_custom.SearchView


class MoviesListFragment : Fragment() {
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MoviesListViewModel>()

    private lateinit var adapter: MovieAdapter

    private val args: MoviesListFragmentArgs by navArgs()

    private val searchCallback = object : SearchView.Callback {
        override fun onQueryChanged(query: String) {
            viewModel.setSearchBy(query)
        }
    }
    private val navCallback = object : MovieAdapter.MovieHolder.NavCallback{
        override fun onItemRecyclerViewClicked(item: MovieData) {
            val direction =
                MoviesListFragmentDirections.actionMoviesListFragmentToDetailsFragment(movieData = item)
            findNavController().navigate(direction)
        }
    }

    private val diffUtilItemCallback = object : DiffUtil.ItemCallback<MovieData>() {
        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isInitialized.not())
            viewModel.init(MovieType.TOP_RATED)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieAdapter(diffUtilItemCallback, navCallback)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.searchView.setCallback(searchCallback)

        setupSwipeToRefresh()

        lifecycleScope.launch {
            viewModel.moviesFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}
