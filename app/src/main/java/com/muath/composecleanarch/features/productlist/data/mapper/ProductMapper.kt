package com.muath.composecleanarch.features.productlist.data.mapper

import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.data.local.entity.ProductEntity
import com.muath.composecleanarch.features.productlist.data.remote.dto.ProductDto

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name ?: "",
        description = description ?: "",
        price = price,
        image = imageUrl ?: ""
    )
}

fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        imageUrl = this.image

    )
}


fun ProductDto.toDomain(): Product {
    return Product(
        id = this.id ?: 0,
        name = this.title ?: "",
        description = this.description ?: "",
        price = this.price ?: 0.0,
        imageUrl = this.image ?: ""
    )
}