package com.subscribe.kmpproject.unittest

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

/**
 * Created on 2023/11/22
 * @author：xiezh
 * @function：static类测试
 */

class UtilKotlinX {
    companion object {
        @JvmStatic
        fun method(): String {
            return "UtilKotlinX.ok()"
        }
    }
}
class UtilsX {
    fun method() {
        UtilKotlinX.method()
    }
}

class ObjectTest {

    @Test
    fun testMethod() {
        // Given
        val utilsX = UtilsX()
        mockkObject(UtilKotlinX.Companion)

        every { UtilKotlinX.method() } returns "Test"

        // When
        utilsX.method()

        // Then
        verify { UtilKotlinX.method() }
        assertEquals("Test", UtilKotlinX.method())
    }
}