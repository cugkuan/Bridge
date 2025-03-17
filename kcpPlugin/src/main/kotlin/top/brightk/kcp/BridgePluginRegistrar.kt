package top.brightk.kcp

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.common.messages.getLogger
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.compiler.plugin.registerInProject
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
class BridgePluginRegistrar  : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true // 适配 Kotlin K2 编译器

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
       // configuration.getLogger().log("Kotlin Compiler Plugin (新 API) 注册成功！")
        IrGenerationExtension.registerExtension(BridgeIrGenerationExtension(configuration.getLogger()))
        // 这里可以注册 IR 处理扩展，比如：
        // registerExtension(MyIrGenerationExtension())
    }
}