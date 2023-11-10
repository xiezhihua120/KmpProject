package com.subscribe.nativebridge.modules.cloudconfig

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
 * @function：云控模块
 */
@Module(name = "GlobalConfig", enableRecvThread = true, enableSendThread = true)
object ConfigBridgeModule {

    @Event("add")
    object EventAdd : EventHandlerBase()

    @Event("remove")
    object EventRemove : EventHandlerBase()

    @Method("init")
    fun init(@Param param: String, @Return methodReturn: MethodReturn<Any?>) {

    }

    @Method("release")
    fun release(@Param param: Any?, @Return methodReturn: MethodReturn<String>) {

    }

}