package com.muath.composecleanarch.features.cart.data.datasource

import com.muath.composecleanarch.features.cart.data.local.dao.CartDao
import com.muath.composecleanarch.features.cart.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Local data source for cart operations.
 * Encapsulates access to [CartDao] and provides
 * a clean API for the repository layer.
 */
interface CartLocalDataSource {

    suspend fun insertCartItem(item: CartItemEntity)

    fun observeCartItems(): Flow<List<CartItemEntity>>

    fun observeCartItem(productId: Int): Flow<CartItemEntity?>

    suspend fun getCartItemByProductId(productId: Int): CartItemEntity?

    suspend fun deleteCartItem(productId: Int)

    suspend fun emptyCart()
}

/**
 * Implementation of [CartLocalDataSource] that uses Room [CartDao].
 */
class CartLocalDataSourceImpl(
    private val cartDao: CartDao
) : CartLocalDataSource {

    override suspend fun insertCartItem(item: CartItemEntity) {
        cartDao.insertCartItem(item)
    }

    override fun observeCartItems(): Flow<List<CartItemEntity>> {
        return cartDao.observeCartItems()
    }

    override fun observeCartItem(productId: Int): Flow<CartItemEntity?> {
        return cartDao.observeCartItem(productId)
    }

    override suspend fun getCartItemByProductId(productId: Int): CartItemEntity? {
        return cartDao.getCartItemByProductId(productId)
    }

    override suspend fun deleteCartItem(productId: Int) {
        cartDao.deleteCartItem(productId)
    }

    override suspend fun emptyCart() {
        cartDao.emptyCart()
    }
}
