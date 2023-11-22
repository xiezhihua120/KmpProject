package com.subscribe.kmpproject.unittest

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

/**
 * Created on 2023/11/22
 * @author：xiezh
 * @function：捕获参数
 */
class Mother {
    fun inform(money: Int) {
        println("Mother.inform $money 元")
    }

    fun giveMoney(): Int {
        return 100
    }
}

class CaptureTest {

    @Test
    fun getName() {
        // 准备
        var mother: Mother = mockk<Mother>()
        val slot = slot<Int>()
        every { mother.inform(capture(slot)) } just Runs

        // 执行
        mother.inform(0)

        // 校验
        assertEquals(0, slot.captured)
    }
}