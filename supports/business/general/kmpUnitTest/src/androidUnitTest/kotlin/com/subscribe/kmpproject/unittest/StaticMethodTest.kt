package com.subscribe.kmpproject.unittest

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

/**
 * Created on 2023/11/22
 * @author：xiezh
 * @function：static类测试
 */

object UtilKotlin {
    @JvmStatic
    fun method(): String {
        return "UtilKotlin.ok()"
    }
}

class Utils {
    fun method() {
        UtilKotlin.method()
    }
}

class StaticClassTest {

    @Test
    fun testMethod() {
        // 准备
        val utils = Utils()
        mockkStatic(UtilKotlin::class)
        every { UtilKotlin.method() } returns "MockResult"

        // 执行
        utils.method()

        // 校验
        verify { UtilKotlin.method() }
        assertEquals("MockResult", UtilKotlin.method())
    }
}