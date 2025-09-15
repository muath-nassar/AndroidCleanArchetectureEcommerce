package com.muath.composecleanarch.features.productslist.domain.usecase

import com.muath.composecleanarch.core.common.AppError
import com.muath.composecleanarch.core.model.Product
import org.junit.Before
import org.junit.Test
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import com.muath.composecleanarch.features.productlist.domain.usecase.GetProductByIdUseCase
import kotlinx.coroutines.test.runTest

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import com.muath.composecleanarch.core.common.Result
import org.mockito.kotlin.verify

class GetProductByIdTest {
    lateinit var repository: ProductRepository
    lateinit var useCase: GetProductByIdUseCase

    @Before
    fun setup(){
        repository = mock()
        useCase = GetProductByIdUseCase(repository)
    }

    // Use Run test whenever you need a coroutine call
    @Test
    fun `should return Ok when repo succeed`() = runTest {

        // Arrange
        val pId = 1
        val expectedProduct = Product(pId, "name", "description",
            10.0, "image")
        whenever(repository.getProductById(pId))
            .thenReturn(Result.Ok(expectedProduct))

        // Act
        val result = useCase(pId)

        // Assert
        verify(repository).getProductById(pId)
        assertEquals(Result.Ok(expectedProduct), result)
    }

    @Test
    fun `should return Err when repo fail`() = runTest {

        // Arrange
        val pId = 1

        whenever(repository.getProductById(pId))
            .thenReturn(Result.Err(AppError.Unknown()))

        // Act
        val result = useCase(pId)

        // Assert
        verify(repository).getProductById(pId)
        assertTrue(result is Result.Err)

    }
}