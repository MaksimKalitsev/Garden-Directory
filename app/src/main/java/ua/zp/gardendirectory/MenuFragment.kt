package ua.zp.gardendirectory

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ua.zp.gardendirectory.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MenuViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isInitialized.not())
            viewModel.init()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menuBerries.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_berriesFragment)
        }
        binding.menuFruits.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_fruitsFragment)
        }
        binding.menuVegetables.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_vegetablesFragment)
        }
        binding.menuPlantsProtection.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_plantsProtectedFragment)
        }

    }

}