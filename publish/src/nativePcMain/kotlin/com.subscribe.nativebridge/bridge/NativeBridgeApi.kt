package com.subscribe.nativebridge.bridge

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.invoke
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created on 2023/11/10
 * @author：xiezh
 * @function：Native Bridge Api
 */
@OptIn(ExperimentalForeignApi::class)
object NativeBridgeApi : NativeBridge, SynchronizedObject() {

    private const val TAG = "JSBridgeApi"
    const val DEBUG = false

    private val methodCallback = MutableStateFlow<MethodCallback?>(null)
    private val eventCallback = MutableStateFlow<EventCallback?>(null)

    init {
        synchronized(this) {
            println("${TAG}: NativeBridgeApi init")
        }
    }

    override fun registerMethodCallback(callback: MethodCallbackPtr) {
        synchronized(this) {
            println("${TAG}: registerMethodCallback")
            methodCallback.update {
                { reqId, module, method, pbBytes, size ->
                    callback(reqId, module, method, pbBytes, size)
                }
            }
        }
    }

    override fun registerEventCallback(callback: EventCallbackPtr) {
        synchronized(this) {
            println("${TAG}: registerEventCallback")
            eventCallback.update {
                { module, method, pbBytes, size ->
                    callback(module, method, pbBytes, size)
                }
            }
        }
    }

    override fun receiveMethod(
        reqId: String,
        module: String,
        method: String,
        pbBytes: CPointer<ByteVar>?,
        size: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun sendEvent(module: String, method: String, pbBytes: CPointer<ByteVar>, size: Int) {
        TODO("Not yet implemented")
    }
}