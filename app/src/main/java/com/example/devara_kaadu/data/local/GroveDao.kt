package com.example.devara_kaadu.data.local

import androidx.room.*
import com.example.devara_kaadu.data.model.Grove
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Sacred Grove CRUD and search operations.
 */
@Dao
interface GroveDao {

    @Query("SELECT * FROM groves ORDER BY name ASC")
    fun getAllGroves(): Flow<List<Grove>>

    @Query("SELECT * FROM groves WHERE id = :id")
    suspend fun getGroveById(id: Int): Grove?

    @Query("SELECT * FROM groves WHERE type = :type ORDER BY name ASC")
    fun getGrovesByType(type: String): Flow<List<Grove>>

    @Query("SELECT * FROM groves WHERE district = :district ORDER BY name ASC")
    fun getGrovesByDistrict(district: String): Flow<List<Grove>>

    @Query("""
        SELECT * FROM groves 
        WHERE name LIKE '%' || :query || '%' 
           OR village LIKE '%' || :query || '%'
           OR district LIKE '%' || :query || '%'
           OR deity LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun searchGroves(query: String): Flow<List<Grove>>

    @Query("SELECT * FROM groves WHERE isVisited = 1")
    fun getVisitedGroves(): Flow<List<Grove>>

    @Query("SELECT COUNT(*) FROM groves")
    suspend fun getGroveCount(): Int

    @Query("SELECT COUNT(*) FROM groves WHERE isVisited = 1")
    suspend fun getVisitedCount(): Int

    @Query("UPDATE groves SET isVisited = 1 WHERE id = :id")
    fun markAsVisited(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(groves: List<Grove>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(grove: Grove)

    @Update
    fun update(grove: Grove)

    @Delete
    fun delete(grove: Grove)
}
