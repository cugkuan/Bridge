package top.brightk.bridge.kcp

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.getLogger
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.util.Logger

@OptIn(ExperimentalCompilerApi::class)
class BridgePluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true // 适配 Kotlin K2 编译器

    override val pluginId: String = "bridge-compiler-plugin"

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val logger = configuration.getLogger()
        logger.warning("BridgeKcp Registrar version 0.2.7 starting...")

        // Standard K2 registration for IrGenerationExtension
        IrGenerationExtension.registerExtension(BridgeIrGenerationExtension(logger))
    }
}


class BridgeIrGenerationExtension(val logger: Logger) : IrGenerationExtension {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        moduleFragment.transform(InitMethodTransformer(pluginContext, logger), null)
        moduleFragment.transform(NavInjectMethodTransformer(pluginContext, logger), null)
    }
}
