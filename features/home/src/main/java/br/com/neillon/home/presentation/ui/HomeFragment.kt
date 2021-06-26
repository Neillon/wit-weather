package br.com.neillon.home.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.neillon.home.databinding.FragmentHomeBinding
import br.com.neillon.home.presentation.ui.cityselection.CitySelectionBottomSheetFragment

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding by lazy { _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        binding.fabCitySelection.setOnClickListener { openCitySelectionBottomSheet() }
    }

    private fun openCitySelectionBottomSheet() {
        CitySelectionBottomSheetFragment.newInstance { cityName ->
            Log.i(
                TAG,
                "openCitySelectionBottomSheet: Should update the current location to $cityName"
            )
        }.show(parentFragmentManager, CitySelectionBottomSheetFragment.TAG)
    }
}
