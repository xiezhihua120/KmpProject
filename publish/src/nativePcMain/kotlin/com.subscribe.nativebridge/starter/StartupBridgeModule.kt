package com.subscribe.nativebridge.starter

import com.subscribe.nativebridge.annotation.Event
import com.subscribe.nativebridge.annotation.Method
import com.subscribe.nativebridge.annotation.Module
import com.subscribe.nativebridge.annotation.Param
import com.subscribe.nativebridge.annotation.Return
import com.subscribe.nativebridge.annotation.MethodReturn

/**
 * Created on 2023/11/10
 * @author：xiezh
 * @function：启动模块
 */
@Module("Startup")
object StartupBridgeModule {

    @Event("add")
    object EventHandlerAdd

    @Event("remove")
    object EventHandlerRemove

    @Method("init")
    fun init(@Param params: String?, @Return methodReturn: MethodReturn<Any>) {

    }

    @Method("release")
    fun release(@Param params: Any, @Return methodReturn: MethodReturn<String?>) {

    }

}