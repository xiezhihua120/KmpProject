@file:OptIn(ExperimentalForeignApi::class)

package com.subscribe.nativebridge.bridge

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValues
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Created on 2023/11/10
 * @author：xiezh
 * @function：NativeBridge
 */
interface NativeBridge {

    /**
     * register method Callback
     */
    fun registerMethodCallback(callback: MethodCallbackPtr)

    /**
     * register event Listener
     */
    fun registerEventCallback(listener: EventCallbackPtr)

    /**
     * receive method request
     */
    fun receiveMethod(
        reqId: String, module: String, method: String, pbBytes: CPointer<ByteVar>?, size: Int
    )

    /**
     * send event request
     */
    fun sendEvent(module: String, method: String, pbBytes: CPointer<ByteVar>, size: Int)
}


typealias MethodCallback = ((reqId: CValues<ByteVar>, module: CValues<ByteVar>, method: CValues<ByteVar>, pbBytes: CPointer<ByteVar>, size: Int) -> Unit)
typealias EventCallback = ((module: CValues<ByteVar>, method: CValues<ByteVar>, pbBytes: CPointer<ByteVar>, size: Int) -> Unit)
typealias MethodCallbackPtr = CPointer<CFunction<MethodCallback>>
typealias EventCallbackPtr = CPointer<CFunction<EventCallback>>
