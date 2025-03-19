package top.brightk.kcp

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.getLogger
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.compiler.plugin.registerInProject
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.util.Logger

@OptIn(ExperimentalCompilerApi::class)
class BridgePluginRegistrar  : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true // 适配 Kotlin K2 编译器
    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        IrGenerationExtension.registerExtension(BridgeIrGenerationExtension(configuration.getLogger()))
    }
}
class BridgeIrGenerationExtension(val logger: Logger) : IrGenerationExtension {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        moduleFragment.transform(InitMethodTransformer(pluginContext,logger), null)
    }
}
