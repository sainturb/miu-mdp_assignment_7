package miu.edu.gardenjournal.ui.garden

import miu.edu.gardenjournal.service.PlantRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import miu.edu.gardenjournal.service.Plant
import miu.edu.gardenjournal.service.PlantDatabase

class GardenViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlantRepository

    val allPlants: LiveData<List<Plant>>

    init {
        val plantDao = PlantDatabase.getDatabase(application).plantDao()
        repository = PlantRepository(application)
        allPlants = repository.allPlants
    }

    fun insert(plant: Plant) = viewModelScope.launch {
        repository.insert(plant)
    }

    fun update(plant: Plant) = viewModelScope.launch {
        repository.update(plant)
    }

    fun delete(plant: Plant) = viewModelScope.launch {
        repository.delete(plant)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun getPlantById(plantId: Int): LiveData<Plant> = viewModelScope.run {
        repository.getPlantById(plantId)
    }
}