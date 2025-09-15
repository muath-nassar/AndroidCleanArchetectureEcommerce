package com.muath.composecleanarch.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muath.composecleanarch.features.productlist.data.local.entity.ProductEntity

/**
 * Skeleton Room database.
 * Features will contribute their Entities + DAOs here later.
 */
@Database(
    entities = [ProductEntity::class], // empty for now
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Future examples:
    // abstract fun productDao(): ProductDao
    // abstract fun userDao(): UserDao
}
