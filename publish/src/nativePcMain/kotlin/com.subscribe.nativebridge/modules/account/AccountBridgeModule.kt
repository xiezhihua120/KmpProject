package com.subscribe.nativebridge.modules.account

import com.subscribe.nativebridge.annotation.Event
import com.subscribe.nativebridge.annotation.Method
import com.subscribe.nativebridge.annotation.Module
import com.subscribe.nativebridge.annotation.Param
import com.subscribe.nativebridge.annotation.Return
import com.subscribe.nativebridge.annotation.MethodReturn
import com.subscribe.nativebridge.event.EventHandlerBase

/**
 * Created on 2023/11/10
 * @author：xiezh
 * @function：账号模块
 */
@Module(name = "Account", enableRecvThread = true, enableSendThread = true)
object AccountBridgeModule {

    @Event("add")
    object EventAdd : EventHandlerBase<String?>()

    @Event("remove")
    object EventRemove : EventHandlerBase<Any?>()

    @Method("init")
    fun init(@Param param: String, @Return methodReturn: MethodReturn<Any?>) {

    }

    @Method("release")
    fun release(@Param param: Any?, @Return methodReturn: MethodReturn<String>) {

    }

}