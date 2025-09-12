package com.muath.composecleanarch.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Skeleton Room database.
 * Features will contribute their Entities + DAOs here later.
 */
@Database(
    entities = [], // empty for now
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    // Future examples:
    // abstract fun productDao(): ProductDao
    // abstract fun userDao(): UserDao
}
