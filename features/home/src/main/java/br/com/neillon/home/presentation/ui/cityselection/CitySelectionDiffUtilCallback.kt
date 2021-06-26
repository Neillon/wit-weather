package br.com.neillon.home.presentation.ui.cityselection

import androidx.recyclerview.widget.DiffUtil

object CitySelectionDiffUtilCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}