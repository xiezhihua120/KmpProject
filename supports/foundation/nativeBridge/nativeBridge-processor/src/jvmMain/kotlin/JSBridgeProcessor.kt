import com.subscribe.nativebridge.annotation.JSBridgeModule
import com.subscribe.nativebridge.annotation.JSBridgeEvent
import com.subscribe.nativebridge.annotation.JSBridgeMethod
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec


class JSBridgeProcessor(private val codeGenerator: CodeGenerator, private val logger: KSPLogger) :
    SymbolProcessor {

    companion object {
        const val PKG_NAME = "com.subscribe.status.jsbridge.annotations"
        const val MODULE_PREFIX = "JSBridgeModule"
        const val FACTORY_NAME = "JSBridgeModuleFactory"

        const val MODULE_NAME_FIELD = "module"
        const val MODULE_METHOD_HANDLERS_FILED = "methodHandlers"
        const val MODULE_EVENT_HANDLERS_FILED = "eventHandlers"
        const val MODULE_INIT_METHOD = "initModule"
        const val FACTORY_MODULES_FIELD = "modules"
        const val FACTORY_INIT_METHOD = "initModules"
        const val FACTORY_GET_METHOD = "getModule"

        val BRIDGE_PROVIDER_CLASS =
            ClassName("com.subscribe.multiplatform.jsbrige", "JSBridgeModuleProvider")
        val BRIDGE_MODULE_CLASS = ClassName("com.subscribe.multiplatform.jsbrige.module", "BridgeModule")
        val STRING_CLASS = ClassName("kotlin", "String")
        val MUTABLE_MAP_CLASS = ClassName("kotlin.collections", "MutableMap")
        val METHOD_HANDLER_CLASS =
            ClassName("com.subscribe.multiplatform.jsbrige.method", "MethodHandler")
        val EVENT_HANDLER_CLASS =
            ClassName("com.subscribe.multiplatform.jsbrige.event", "EventHandlerBase")
        val JS_CALLBACK_CLASS = ClassName("com.subscribe.multiplatform.jsbrige", "JsCallbackInvoker")
        val MUTABLE_MAP_METHODS_CLASS =
            MUTABLE_MAP_CLASS.parameterizedBy(STRING_CLASS, METHOD_HANDLER_CLASS)
        val MUTABLE_MAP_EVENTS_CLASS =
            MUTABLE_MAP_CLASS.parameterizedBy(STRING_CLASS, EVENT_HANDLER_CLASS)
        val MUTABLE_MAP_MODULES_CLASS =
            MUTABLE_MAP_CLASS.parameterizedBy(STRING_CLASS, BRIDGE_MODULE_CLASS)
    }

    private var invoked = false
    private var moduleFileSpecList: MutableList<FileSpec> = mutableListOf()


    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) return emptyList()
        invoked = true

        val symbols = resolver.getSymbolsWithAnnotation(JSBridgeModule::class.qualifiedName!!)
        logger.warn(symbols.toList().toString())
        symbols.filter { it is KSClassDeclaration }.map { it to (it as KSClassDeclaration) }
            .forEach { entry ->
                val ksClass = entry.first
                val ksClassDec = entry.second
                val pkgName = ksClassDec.declarations.first().packageName.asString()
                val className = ksClass.toString()

                /**
                 * 模块名称
                 */
                val jsModule = ksClass.getAnnotationsByType(JSBridgeModule::class).firstOrNull()
                    ?: return@forEach
                logger.warn("jsModule: [${jsModule.name}]")
                val fileSpec = FileSpec.builder(PKG_NAME, "${MODULE_PREFIX}${jsModule.name}")
                    .addImport(pkgName, className)
                    .addImport(JS_CALLBACK_CLASS.packageName, JS_CALLBACK_CLASS.simpleName)
                val classSpec = TypeSpec.objectBuilder("${MODULE_PREFIX}${jsModule.name}")
                    .addModifiers(KModifier.INTERNAL).addSuperinterface(BRIDGE_MODULE_CLASS)
                // 属性module
                val module =
                    PropertySpec.builder(MODULE_NAME_FIELD, STRING_CLASS, KModifier.OVERRIDE)
                        .initializer("%S", jsModule.name)
                // 属性methodHandlers
                val methodHandlers = PropertySpec.builder(
                    MODULE_METHOD_HANDLERS_FILED, MUTABLE_MAP_METHODS_CLASS, KModifier.OVERRIDE
                ).initializer("mutableMapOf()")
                // 属性methodHandlers
                val eventHandlers = PropertySpec.builder(
                    MODULE_EVENT_HANDLERS_FILED, MUTABLE_MAP_EVENTS_CLASS, KModifier.OVERRIDE
                ).initializer("mutableMapOf()")
                // 方法initModule
                val initModule = FunSpec.builder(MODULE_INIT_METHOD)

                // 模块方法
                ksClassDec.declarations.filter { it is KSFunctionDeclaration }.forEach { kfun ->
                    val jsMethod = kfun.getAnnotationsByType(JSBridgeMethod::class).firstOrNull()
                        ?: return@forEach
                    logger.warn("jsMethod: [${jsModule.name}-${jsMethod.name}]")
                    initModule.addCode(
                        """
                        |// Method: ${jsMethod.name}
                        |methodHandlers["${jsMethod.name}"] = object: MethodHandler() {
                        |    override fun handle(reqId: String, module: String, params: ByteArray, callback: JsCallbackInvoker?) {
                        |        $ksClass.${kfun.simpleName.getShortName()}(params)  {
                        |            this.sendCallback(reqId, it, callback)
                        |        }
                        |    }
                        |}
                        |
                        |""".trimMargin()
                    )
                }

                // 模块事件
                ksClassDec.declarations.filter { it is KSClassDeclaration }.forEach { eventClass ->
                    val jsEvent = eventClass.getAnnotationsByType(JSBridgeEvent::class).firstOrNull()
                        ?: return@forEach
                    logger.warn("jsEvent: [${jsModule.name}-${jsEvent.name}]")
                    initModule.addCode(
                        """
                        |// Event: ${jsEvent.name}
                        |eventHandlers["${jsEvent.name}"] = $ksClass.${eventClass.simpleName.getShortName()}
                        |
                        |""".trimMargin()
                    )
                }

                // 初始化
                initModule.addCode(
                    """
                        |
                        |methodHandlers.forEach { it.value.setModule(module).setMethod(it.key) }
                        |eventHandlers.forEach { it.value.setModule(module).setMethod(it.key) }
                        |
                        |""".trimMargin()
                )

                classSpec.addProperty(module.build())
                classSpec.addProperty(methodHandlers.build())
                classSpec.addProperty(eventHandlers.build())
                classSpec.addFunction(initModule.build())
                fileSpec.addType(classSpec.build())
                moduleFileSpecList.add(fileSpec.build())
            }
        return emptyList()
    }

    override fun finish() {
        super.finish()
        moduleFileSpecList.forEach {
            writeFileSpec(it)
        }
        writeFileSpec(getModuleFactory(moduleFileSpecList).build())
    }

    private fun writeFileSpec(fileSpec: FileSpec) {
        val file = codeGenerator.createNewFile(
            Dependencies.ALL_FILES, fileSpec.packageName, fileSpec.name
        )
        file.use {
            val content = fileSpec.toString().toByteArray()
            it.write(content)
        }
    }

    private fun getModuleFactory(moduleFileSpecList: MutableList<FileSpec>): FileSpec.Builder {
        val fileSpec = FileSpec.builder(PKG_NAME, FACTORY_NAME)
        // 类
        val classSpec =
            TypeSpec.objectBuilder(FACTORY_NAME)
                .addModifiers(KModifier.INTERNAL)
                .addSuperinterface(BRIDGE_PROVIDER_CLASS)

        // 属性
        val modules = PropertySpec.builder(
            FACTORY_MODULES_FIELD, MUTABLE_MAP_MODULES_CLASS, KModifier.PRIVATE
        ).initializer("mutableMapOf()")

        // 方法
        val initModules = FunSpec.builder(FACTORY_INIT_METHOD)
        moduleFileSpecList.forEach {
            initModules.addStatement("${it.name}.${MODULE_INIT_METHOD}()")
        }
        moduleFileSpecList.forEach {
            initModules.addStatement("${FACTORY_MODULES_FIELD}[${it.name}.${MODULE_NAME_FIELD}] = ${it.name}")
        }

        // 方法
        val getModule = FunSpec.builder(FACTORY_GET_METHOD)
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(MODULE_NAME_FIELD, String::class)
            .returns(BRIDGE_MODULE_CLASS.copy(nullable = true))
            .addStatement("return modules[${MODULE_NAME_FIELD}]")

        classSpec.addProperty(modules.build())
        classSpec.addFunction(initModules.build())
        classSpec.addFunction(getModule.build())
        fileSpec.addType(classSpec.build())
        return fileSpec
    }

}

class JSBridgeProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return JSBridgeProcessor(environment.codeGenerator, environment.logger)
    }
}
