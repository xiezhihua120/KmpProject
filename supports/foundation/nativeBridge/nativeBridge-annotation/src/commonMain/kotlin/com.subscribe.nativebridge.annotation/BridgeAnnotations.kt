package com.subscribe.nativebridge.annotation

import kotlin.reflect.KClass

/**
 * 模块定义
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BridgeModule(val name: String)

/**
 * 事件定义
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BridgeEvent(val name: String, val clazz: KClass<*>)

/**
 * 方法定义
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class BridgeMethod(val name: String)

/**
 * 方法入参定义
 */
@Target(AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.SOURCE)
annotation class BridgeParam

/**
 * 方法返回定义
 */
@Target(AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.SOURCE)
annotation class BridgeReturn