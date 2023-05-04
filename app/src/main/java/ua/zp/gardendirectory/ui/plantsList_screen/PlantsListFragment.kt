package ua.zp.gardendirectory.ui.plantsList_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.zp.gardendirectory.R
import ua.zp.gardendirectory.data.models.PlantData
import ua.zp.gardendirectory.databinding.FragmentMenuBinding
import ua.zp.gardendirectory.databinding.FragmentPlantsListBinding
import ua.zp.gardendirectory.ui.PlantAdapter
import ua.zp.gardendirectory.ui.details_screen.PlantsListState

enum class RequestState{
    LOADING, SUCCESS, ERROR;
}
class PlantsListFragment : Fragment() {
    private var _binding: FragmentPlantsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PlantsListViewModel> ()

    private lateinit var adapter: PlantAdapter

    private val diffUtilItemCallback = object : DiffUtil.ItemCallback<PlantData>() {
        override fun areItemsTheSame(oldItem: PlantData, newItem: PlantData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlantData, newItem: PlantData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isInitialized.not())
            viewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlantsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlantAdapter(diffUtilItemCallback)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        setupSwipeToRefresh()

        lifecycleScope.launch {
            viewModel.plantsFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

//    private fun setupSearchInput() {
//        binding.searchEditText.addTextChangedListener {
//            viewModel.setSearchBy(it.toString())
//        }
//    }
//

//    private val stateObserver = Observer<PlantsListState>{
//        when(it.requestState){
//            RequestState.LOADING->{
//                binding.progressBar.visibility = View.VISIBLE
//            }
//            RequestState.SUCCESS->{
//                binding.progressBar.visibility = View.GONE
//                adapter.submitData(lifecycle, plantsFlow)
//            }
//            RequestState.ERROR->{
//                binding.progressBar.visibility = View.GONE
//                showSnackbar()
//            }
//        }
//    }
    private fun showSnackbar() {
        val mySnackbar =
            Snackbar.make(binding.plantListLayout, R.string.error_snackbar, Snackbar.LENGTH_INDEFINITE)
        mySnackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
        mySnackbar.show()
    }
}
