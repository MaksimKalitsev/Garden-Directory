//package ua.zp.gardendirectory.ui.menu_screen
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import ua.zp.gardendirectory.MovieType
//import ua.zp.gardendirectory.R
//import ua.zp.gardendirectory.databinding.FragmentMenuBinding
//
//class MenuFragment : Fragment() {
//
//    private var _binding: FragmentMenuBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel by viewModels<MenuViewModel>()
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (viewModel.isInitialized.not())
//            viewModel.init()
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentMenuBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.menuBerries.setOnClickListener {
//            toListScreen(MovieType.TOP_RATED)
//        }
//        binding.menuVegetables.setOnClickListener {
//            toListScreen(MovieType.NOW_PLAYING)
//        }
//        binding.menuFruits.setOnClickListener {
//            toListScreen(MovieType.POPULAR)
//        }
//        binding.menuPlantsProtection.setOnClickListener {
//            toListScreen(MovieType.UPCOMING)
//        }
//
//
//    }
//
//    private fun toListScreen(type: MovieType) {
//        findNavController().navigate(
//            MenuFragmentDirections.actionMenuFragmentToMoviesListFragment(type)
//        )
//
//    }
//
//}