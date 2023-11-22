package com.subscribe.kmpproject.unittest
import com.subscribe.kmpproject.unit.Kid
import com.subscribe.kmpproject.unit.Mother
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

/**
 * Created on 2023/11/22
 * @author：xiezh
 * @function：行为模拟
 */
class CommonTest {

    @Test
    fun testExample() {
        // prepare
        val mother = mockk<Mother>()
        val kid = Kid(mother)
        every { mother.giveMoney() } returns 30

        // execute
        kid.wantMoney()

        // assert
        verify {
            kid.wantMoney()
        }
        assertEquals(30, kid.money)
    }

}