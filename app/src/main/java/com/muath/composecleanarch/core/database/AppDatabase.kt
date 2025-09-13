package com.muath.composecleanarch.core.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

/**
 * Skeleton Room database.
 * Features will contribute their Entities + DAOs here later.
 */
@Database(
    entities = [DummyEntity::class], // empty for now
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Future examples:
    // abstract fun productDao(): ProductDao
    // abstract fun userDao(): UserDao
}

@Entity
data class DummyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
