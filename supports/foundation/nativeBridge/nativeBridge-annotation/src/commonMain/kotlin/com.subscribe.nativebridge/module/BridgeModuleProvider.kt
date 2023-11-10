package com.subscribe.nativebridge.module

/**
 * Created on 2023/9/25
 * @author：xiezh
 * @function：模块提供器
 */
interface BridgeModuleProvider {
    /**
     * 获取JSBridgeModule模块
     */
    fun getModule(module: String): BridgeModule?
}