package ua.zp.moviedbportfolioproject.ui.moviesList_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.zp.moviedbportfolioproject.MovieType
import ua.zp.moviedbportfolioproject.data.models.MovieData
import ua.zp.moviedbportfolioproject.databinding.FragmentMoviesListBinding
import ua.zp.moviedbportfolioproject.ui.MovieAdapter
import ua.zp.moviedbportfolioproject.ui.view_custom.SearchView

@AndroidEntryPoint
class MoviesListFragment : Fragment() {
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MoviesListViewModel>()

    private lateinit var adapter: MovieAdapter


    private val searchCallback = object : SearchView.Callback {
        override fun onQueryChanged(query: String) {
            viewModel.setSearchBy(query)
        }
    }
    private val navCallback = object : MovieAdapter.MovieHolder.NavCallback {
        override fun onItemRecyclerViewClicked(item: MovieData) {
            val direction =
                MoviesListFragmentDirections.actionMoviesListFragmentToDetailsFragment(movieData = item)
            findNavController().navigate(direction)
        }

        override fun addFavoriteMovie(movie: MovieData) {
            viewModel.addFavoriteMovie(movie)
        }

        override fun removeFavoriteMovie(movie: MovieData) {
            viewModel.deleteFavoriteMovie(movie)
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
        with(binding) {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            searchView.setCallback(searchCallback)
        }

        setupSwipeToRefresh()

        MovieType.getTypeByMenuId(binding.bottomNavView.selectedItemId)?.let {
            viewModel.init(it)
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launch {
            viewModel.moviesFlow.collectLatest {
                adapter.submitData(it)
            }

        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            MovieType.getTypeByMenuId(item.itemId)?.let { selectedMovieType ->
                viewModel.setMovieType(selectedMovieType)
            }
            true
        }

    }
    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}
