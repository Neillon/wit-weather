package br.com.neillon.home.presentation.ui.cityselection

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.neillon.home.R
import br.com.neillon.home.databinding.FragmentCitySelectionBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CitySelectionBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CitySelectionBottomSheet"

        fun newInstance(onCitySelected: (String) -> Unit) =
            CitySelectionBottomSheetFragment().apply {
                this.onItemSelected = onCitySelected
            }
    }

    private var _binding: FragmentCitySelectionBinding? = null
    private val binding by lazy { _binding!! }

    private lateinit var onItemSelected: (String) -> Unit

    private val adapter by lazy {
        CitySelectionAdapter() { cityName ->
            onItemSelected(cityName)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCitySelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        binding.recyclerViewCities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CitySelectionBottomSheetFragment.adapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val parentLayout = requireView().parent as? View
            parentLayout?.let { view ->
                val behaviour = BottomSheetBehavior.from(view)
                val layoutParams = view.layoutParams
                layoutParams.height =
                    (Resources.getSystem().displayMetrics.heightPixels * 0.85).toInt()
                view.layoutParams = layoutParams
                view.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.round_bottom_sheet_background
                    )
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            }
        }
        return dialog
    }
}