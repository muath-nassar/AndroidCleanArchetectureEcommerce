package com.muath.composecleanarch.features.productslist.domain.usecase

import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import com.muath.composecleanarch.features.productlist.domain.usecase.GetProductsUseCase
import com.muath.composecleanarch.core.common.Result
import com.muath.composecleanarch.core.model.Product
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetProductsUseCaseTest {
    private lateinit var repository: ProductRepository
    private lateinit var useCase: GetProductsUseCase

    @Before
    fun setup(){
        repository = mock()
        useCase = GetProductsUseCase(repository)
    }

    @Test
    fun shouldReturnData()= runTest{
    // Arrange
        val expected = listOf(
            Product(1, "name", "description", 10.0, "image"),
            Product(2, "name", "description", 10.0, "image"),
            )
        whenever(repository.getProducts())
            .thenReturn(flowOf(Result.Ok(expected)))

    // Act
        val resultFlow = useCase()
        val results = resultFlow.toList()

    // Assert
        verify(repository).getProducts()
        assertEquals(listOf(Result.Ok(expected)), results)

    }
}