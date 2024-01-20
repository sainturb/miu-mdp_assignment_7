package miu.edu.gardenjournal.ui.garden

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import miu.edu.gardenjournal.R
import miu.edu.gardenjournal.databinding.FragmentGardenBinding
import miu.edu.gardenjournal.service.Plant
import java.lang.NullPointerException

class GardenFragment : Fragment() {

    private var _binding: FragmentGardenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val samplePlants = mutableListOf<Plant>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        println("on create view")
        val gardenViewModel = ViewModelProvider(this)[GardenViewModel::class.java]

        samplePlants.add(Plant(id = 1, name = "Rose", type = "Flower", wateringFrequency = 2, plantingDate = "2023-01-01"))
        samplePlants.add(Plant(id = 2, name = "Tomato", type = "Vegetable", wateringFrequency = 3, plantingDate = "2023-02-15"))
        samplePlants.add(Plant(id = 3, name = "Basil", type = "Herb", wateringFrequency = 1, plantingDate = "2023-03-10"))

        for (plant in samplePlants) {
            try {
                gardenViewModel.insert(plant)
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
            }
        }

        _binding = FragmentGardenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = PlantAdapter { plant -> adapterOnClick(plant) }
        val recyclerView = root.findViewById<RecyclerView>(R.id.list)
        recyclerView.adapter = adapter

        gardenViewModel.allPlants.observe(viewLifecycleOwner) { plants ->
            plants?.let {
                adapter.submitList(it as MutableList<Plant>)
            }
        }

        val addButton: FloatingActionButton = binding.add

        addButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Create a new Plant")
            builder.setMessage("Fill out the details of the new plant")

            val inflaterDialog = layoutInflater
            val dialogLayout = inflaterDialog.inflate(R.layout.add_plant, null)
            builder.setView(dialogLayout)

            val name = dialogLayout.findViewById<EditText>(R.id.formName)
            val type = dialogLayout.findViewById<EditText>(R.id.formType)
            val freq = dialogLayout.findViewById<EditText>(R.id.formFrequency)
            val date = dialogLayout.findViewById<EditText>(R.id.formDate)

            builder.setPositiveButton("Create") { _, _ ->
                val plant = Plant(
                    name = name.text.toString(),
                    type = type.text.toString(),
                    wateringFrequency = freq.text.toString().toInt(),
                    plantingDate = date.text.toString()
                )
                gardenViewModel.insert(plant)
                name.text.clear()
                type.text.clear()
                freq.text.clear()
                date.text.clear()
                Toast.makeText(requireContext(), "Blog is successfully added", Toast.LENGTH_SHORT)
                    .show()
            }
            builder.show()
        }

        return root
    }

    private fun adapterOnClick(plant: Plant) {
        println(plant)
        val navController = this.findNavController()
        val bundle = Bundle()
        bundle.putInt("plantId", plant.id)
        navController.navigate(R.id.action_navigation_garden_to_navigation_plant, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}