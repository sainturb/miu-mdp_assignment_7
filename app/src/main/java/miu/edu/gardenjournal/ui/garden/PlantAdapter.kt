package miu.edu.gardenjournal.ui.garden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import miu.edu.gardenjournal.R
import miu.edu.gardenjournal.service.Plant

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil


class PlantAdapter(private val onClick: (Plant) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Plant, PlantAdapter.PlantViewHolder>(PlantDiffCallback) {

    /* ViewHolder for Plant, takes in the inflated view and the onClick behavior. */
    class PlantViewHolder(itemView: View, val onClick: (Plant) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private var currentPlant: Plant? = null
        val name: TextView = itemView.findViewById(R.id.name)
        init {
            itemView.setOnClickListener {
                currentPlant?.let {
                    onClick(it)
                }
            }
        }

        /* Bind plant name and image. */
        @SuppressLint("SetTextI18n")
        fun bind(plant: Plant) {
            currentPlant = plant
            name.text = plant.name
        }

        private fun adapterOnClick(ingredient: String) {

        }
    }

    /* Creates and inflates view and return PlantViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view, onClick)
    }

    /* Gets current plant and uses it to bind view. */
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant)
    }
}

object PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {
    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.id == newItem.id
    }
}