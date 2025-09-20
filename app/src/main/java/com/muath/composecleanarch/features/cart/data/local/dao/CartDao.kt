package com.muath.composecleanarch.features.cart.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.muath.composecleanarch.features.cart.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing Cart-related database operations.
 *
 * This interface defines all the queries and mutations that can be
 * performed on the `cart_items` table in the local Room database.
 *
 * Design notes:
 * - `productId` is the primary key of CartItemEntity.
 * - Insert operations use `OnConflictStrategy.REPLACE`, so calling `insertCartItem`
 *   acts as an "upsert": it will insert a new row if none exists, or replace
 *   the existing row if the same productId is already present.
 * - Queries that return `Flow` allow the UI to observe real-time changes
 *   in the cart, automatically updating the screen when data changes.
 */
@Dao
interface CartDao {

    /**
     * Inserts a new cart item or replaces the existing row
     * if the productId already exists in the cart.
     *
     * @param item the CartItemEntity to insert or update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItemEntity)

    /**
     * Observes all cart items with their associated products.
     * This emits a new list every time the cart or products change.
     *
     * @return a Flow of list of CartItemWithProduct
     */
    @Transaction
    @Query("SELECT * FROM cart_items")
    fun observeCartItems(): Flow<List<CartItemEntity>>

    /**
     * Observes a single cart item and its associated product.
     * Useful for updating a specific product row without
     * recomposing the entire cart list.
     *
     * @param productId the ID of the product to observe
     * @return a Flow of CartItemWithProduct or null if not in cart
     */
    @Transaction
    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    fun observeCartItem(productId: Int): Flow<CartItemEntity?>

    /**
     * Retrieves a single cart item entity directly (no product relation).
     *
     * @param productId the ID of the product in the cart
     * @return the CartItemEntity or null if not found
     */
    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: Int): CartItemEntity?

    /**
     * Deletes a specific cart item from the cart.
     *
     * @param productId the ID of the product to remove
     */
    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItem(productId: Int)

    /**
     * Deletes all cart items, leaving the cart empty.
     */
    @Query("DELETE FROM cart_items")
    suspend fun emptyCart()
}
