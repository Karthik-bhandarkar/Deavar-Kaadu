package com.example.devara_kaadu.data.repository

import com.example.devara_kaadu.data.local.GroveDao
import com.example.devara_kaadu.data.model.Grove
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GroveRepository(private val groveDao: GroveDao) {
    fun getAllGroves(): Flow<List<Grove>> = groveDao.getAllGroves()
    fun getGrovesByType(type: String): Flow<List<Grove>> = groveDao.getGrovesByType(type)
    fun getGrovesByDistrict(district: String): Flow<List<Grove>> = groveDao.getGrovesByDistrict(district)
    fun searchGroves(query: String): Flow<List<Grove>> = groveDao.searchGroves(query)
    fun getVisitedGroves(): Flow<List<Grove>> = groveDao.getVisitedGroves()
    suspend fun getGroveById(id: Int): Grove? = groveDao.getGroveById(id)
    suspend fun markAsVisited(id: Int) = withContext(Dispatchers.IO) { groveDao.markAsVisited(id) }
    suspend fun getVisitedCount(): Int = groveDao.getVisitedCount()
    suspend fun getTotalCount(): Int = groveDao.getGroveCount()
}
