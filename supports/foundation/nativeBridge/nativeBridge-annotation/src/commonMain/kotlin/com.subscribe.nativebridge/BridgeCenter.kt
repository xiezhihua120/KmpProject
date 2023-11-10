package com.subscribe.nativebridge

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf

/**
 * Created on 2023/11/10
 * @author：xiezh
 * @function：桥接转化管理中心
 */
object BridgeCenter: SynchronizedObject() {
    private var methodListener: MethodReturnListener? = null
    private var eventListener: EventReceiveListener? = null

    /**
     * 方法结果监听
     */
    fun registerMethodReturnListener(methodListener: MethodReturnListener) {
        synchronized(this) {
            this.methodListener = methodListener
        }
    }

    /**
     * 事件发送监听
     */
    fun registerEventSendListener(eventListener: EventReceiveListener) {
        synchronized(this) {
            this.eventListener = eventListener
        }
    }

    internal fun onMethodReturn(reqId: String, module: String, method: String, data: ByteArray) {
        methodListener?.invoke(reqId, module, method, data)
    }

    internal fun onEventSend(reqId: String, module: String, method: String, data: ByteArray) {
        eventListener?.invoke(reqId, module, method, data)
    }
}

typealias MethodReturnListener = (reqId: String, module: String, method: String, data: ByteArray) -> Unit
typealias EventReceiveListener = (reqId: String, module: String, method: String, data: ByteArray) -> Unit

inline fun < reified T : Any> T.toPBArray(): ByteArray {
    return ProtoBuf.encodeToByteArray(this)
}

inline fun <reified T : Any> ByteArray.fromPBArray(): T {
    return ProtoBuf.decodeFromByteArray(this)
}