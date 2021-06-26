package br.com.neillon.home.presentation.ui.cityselection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.neillon.core.entities.Cities
import br.com.neillon.home.databinding.ItemCityBinding


class CitySelectionAdapter(
    var onCitySelected: (String) -> Unit
) :
    ListAdapter<String, CitySelectionAdapter.CitySelectionViewHolder>(CitySelectionDiffUtilCallback) {

    init {
        val cities = Cities.values().map { it.cityName }
        submitList(cities)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemCityBinding.inflate(inflater, parent, false)

        return CitySelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitySelectionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class CitySelectionViewHolder(var binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cityName: String) {
            binding.textViewCityName.text = cityName
            binding.root.setOnClickListener { onCitySelected(cityName) }
        }
    }
}