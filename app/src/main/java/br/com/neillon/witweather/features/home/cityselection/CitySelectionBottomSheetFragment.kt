package br.com.neillon.witweather.features.home.cityselection

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.neillon.witweather.databinding.FragmentCitySelectionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CitySelectionBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CitySelectionBottomSheet"

        fun newInstance(onClose: () -> Unit) = CitySelectionBottomSheetFragment().apply {
            this.onClose = onClose
        }
    }

    private var _binding: FragmentCitySelectionBinding? = null
    private val binding by lazy { _binding!! }

    private lateinit var onClose: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCitySelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onClose()
    }
}