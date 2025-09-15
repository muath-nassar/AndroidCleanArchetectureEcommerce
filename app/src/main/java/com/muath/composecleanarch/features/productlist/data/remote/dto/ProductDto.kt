package com.muath.composecleanarch.features.productlist.data.remote.dto

/**
 * "id": 0,
 * "title": "string",
 * "price": 0.1,
 * "description": "string",
 * "category": "string",
 * "image": "http://example.com"
 *
 */

data class ProductDto (
    val id: Int?,
    val title: String?,
    val price: Double?,
    val description: String?,
    val category: String?,
    val image: String?

)