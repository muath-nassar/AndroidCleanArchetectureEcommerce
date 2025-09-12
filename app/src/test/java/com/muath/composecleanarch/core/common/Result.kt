package com.muath.composecleanarch.core.common
import org.junit.Test
import org.junit.Assert.*

class ResultTest {

    @Test
    fun testMapOk(){
        val result: Result<Int> = Result.Ok(10)
        val actual  = result.map { v -> "I have $v" }
        assertEquals("I have 10", actual.getOrNull())
    }
    @Test
    fun testMapError(){
        val result: Result<Int> = Result.Err(AppError.Unknown())
        val actual  = result.map { v -> "I have $v" }
        assertTrue(actual is Result.Err)
        assertNotEquals("I have 10", actual.getOrNull())
        assertNull(actual.getOrNull())
    }
}