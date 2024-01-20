package miu.edu.gardenjournal.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants")
    fun getAllPlants(): LiveData<List<Plant>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plant: Plant)
    @Update
    suspend fun update(plant: Plant)
    @Query("DELETE FROM plants") suspend fun deleteAll()
    @Query("DELETE FROM plants WHERE id = :plantId") suspend fun delete(plantId: Int)
    @Query("SELECT * FROM plants WHERE id = :plantId") fun getPlantById(plantId: Int): LiveData<Plant>
}