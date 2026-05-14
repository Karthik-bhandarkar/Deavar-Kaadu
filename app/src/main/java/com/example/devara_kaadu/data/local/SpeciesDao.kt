package com.example.devara_kaadu.data.local

import androidx.room.*
import com.example.devara_kaadu.data.model.Species
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Species CRUD and search/filter operations.
 */
@Dao
interface SpeciesDao {

    @Query("SELECT * FROM species ORDER BY name ASC")
    fun getAllSpecies(): Flow<List<Species>>

    @Query("SELECT * FROM species WHERE id = :id")
    suspend fun getSpeciesById(id: Int): Species?

    @Query("SELECT * FROM species WHERE type = :type ORDER BY name ASC")
    fun getSpeciesByType(type: String): Flow<List<Species>>

    @Query("""
        SELECT * FROM species 
        WHERE name LIKE '%' || :query || '%' 
           OR scientificName LIKE '%' || :query || '%'
           OR kannadaName LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun searchSpecies(query: String): Flow<List<Species>>

    @Query("SELECT COUNT(*) FROM species")
    suspend fun getSpeciesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(species: List<Species>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(species: Species)

    @Update
    fun update(species: Species)
}
