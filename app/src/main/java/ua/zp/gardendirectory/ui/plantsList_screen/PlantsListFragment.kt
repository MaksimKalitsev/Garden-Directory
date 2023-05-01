package ua.zp.gardendirectory.ui.plantsList_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ua.zp.gardendirectory.R
import ua.zp.gardendirectory.databinding.FragmentMenuBinding
import ua.zp.gardendirectory.databinding.FragmentPlantsListBinding
import ua.zp.gardendirectory.ui.PlantAdapter

class PlantsListFragment : Fragment() {
    private var _binding: FragmentPlantsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PlantsListViewModel> ()

    private lateinit var adapter: PlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isInitialized.not())
            viewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlantAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter


//        binding.titleVegetables.setOnClickListener {
//            findNavController().navigate(R.id.action_plantsListFragment_to_detailsFragment)
//        }
    }

}