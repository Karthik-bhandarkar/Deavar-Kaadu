package com.example.devara_kaadu.data.repository

import com.example.devara_kaadu.data.local.SpeciesDao
import com.example.devara_kaadu.data.model.Species
import kotlinx.coroutines.flow.Flow

class SpeciesRepository(private val speciesDao: SpeciesDao) {
    fun getAllSpecies(): Flow<List<Species>> = speciesDao.getAllSpecies()
    fun getSpeciesByType(type: String): Flow<List<Species>> = speciesDao.getSpeciesByType(type)
    fun searchSpecies(query: String): Flow<List<Species>> = speciesDao.searchSpecies(query)
    suspend fun getSpeciesById(id: Int): Species? = speciesDao.getSpeciesById(id)
    suspend fun getTotalCount(): Int = speciesDao.getSpeciesCount()
}
