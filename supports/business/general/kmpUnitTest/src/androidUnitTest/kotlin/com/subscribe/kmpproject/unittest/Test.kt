package com.subscribe.kmpproject.unittest
import com.subscribe.kmpproject.unit.Kid
import com.subscribe.kmpproject.unit.Mother
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class CommonGreetingTest {

    @Test
    fun testExample() {
        // prepare
        val mother = mockk<Mother>()
        val kid = Kid(mother)
        every { mother.giveMoney() } returns 30

        // execute
        kid.wantMoney()

        // assert
        assertEquals(30, kid.money)
    }

}