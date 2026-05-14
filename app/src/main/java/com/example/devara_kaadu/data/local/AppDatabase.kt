package com.example.devara_kaadu.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.devara_kaadu.data.model.*
import com.example.devara_kaadu.utils.JsonLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Main Room database for Devara-Kaadu.
 * Uses a singleton pattern with a prepopulate callback that loads
 * data from local JSON assets on first launch.
 */
@Database(
    entities = [Grove::class, Species::class, Alert::class, UserProgress::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groveDao(): GroveDao
    abstract fun speciesDao(): SpeciesDao
    abstract fun alertDao(): AlertDao
    abstract fun userProgressDao(): UserProgressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "devara_kaadu_db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Preload data from JSON assets on first launch
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { database ->
                                    preloadData(context, database)
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                // Trigger data load if DB just created
                CoroutineScope(Dispatchers.IO).launch {
                    if (instance.groveDao().getGroveCount() == 0) {
                        preloadData(context, instance)
                    }
                }
                instance
            }
        }

        /**
         * Loads groves, species, and initial user progress from JSON assets.
         */
        private suspend fun preloadData(context: Context, db: AppDatabase) {
            try {
                // Load groves from assets/groves_data.json
                val groves = JsonLoader.loadGroves(context)
                if (groves.isNotEmpty()) {
                    db.groveDao().insertAll(groves)
                }

                // Load species from assets/species_data.json
                val species = JsonLoader.loadSpecies(context)
                if (species.isNotEmpty()) {
                    db.speciesDao().insertAll(species)
                }

                // Create initial user progress row
                db.userProgressDao().upsert(
                    UserProgress(
                        id = 1,
                        grovesVisited = 0,
                        speciesScanned = 0,
                        alertsReported = 0,
                        legendsRead = 0,
                        totalPoints = 0,
                        badgesEarnedJson = "[]",
                        lastActiveDate = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
