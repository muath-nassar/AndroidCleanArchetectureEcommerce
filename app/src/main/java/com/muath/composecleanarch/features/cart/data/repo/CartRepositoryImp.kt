package com.muath.composecleanarch.features.cart.data.repo

import com.muath.composecleanarch.features.cart.data.datasource.CartLocalDataSource
import com.muath.composecleanarch.features.cart.data.mapper.toDomain
import com.muath.composecleanarch.features.cart.data.mapper.toEntity
import com.muath.composecleanarch.features.cart.domain.entity.CartItem
import com.muath.composecleanarch.features.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImp
    @Inject constructor(
        private val localDataSource: CartLocalDataSource,
    ) : CartRepository {
    override suspend fun addUpdateCartItem(cartItem: CartItem) {
        if (cartItem.quantity == 0){
            localDataSource.deleteCartItem(cartItem.productId)
            return
        }
        localDataSource.insertCartItem(cartItem.toEntity())
    }

    override fun observeCartItem(itemId: Int): Flow<CartItem?> {
        return localDataSource.observeCartItem(itemId).map { cartItemEntity ->
            cartItemEntity?.toDomain()
        }
    }

    override fun observeCart(): Flow<List<CartItem>> {
        return localDataSource.observeCartItems().map { cartItemEntities ->
            cartItemEntities.map { it.toDomain() }
        }
    }


    override suspend fun emptyCart() {
        localDataSource.emptyCart()
    }
}