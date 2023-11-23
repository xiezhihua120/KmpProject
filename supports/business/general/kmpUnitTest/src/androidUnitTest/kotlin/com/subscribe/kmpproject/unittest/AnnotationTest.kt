package com.subscribe.kmpproject.unittest

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import kotlin.test.Test

/**
 * Created on 2023/11/22
 * @author：xiezh
 * @function：注解构造
 */
class Car {
    fun getName(): String {
        return "NewCar"
    }
}

class AnnotationTest {

    @MockK
    lateinit var car: Car

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getName() {
        every { car.getName() } returns "MyCar"
        val name = car.getName()
        assertEquals("MyCar", name)
    }
}