package  top.brightk.bridge.kcp

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.getLogger
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.util.Logger



@OptIn(ExperimentalCompilerApi::class)
class BridgePluginRegistrar  : ComponentRegistrar, CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true // 适配 Kotlin K2 编译器
    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        IrGenerationExtension.registerExtension(BridgeIrGenerationExtension(configuration.getLogger()))
    }
    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        // iOS 走这里的插件加载
        IrGenerationExtension.registerExtension(
            project,
            BridgeIrGenerationExtension(configuration.getLogger())
        )
    }
}
class BridgeIrGenerationExtension(val logger: Logger) : IrGenerationExtension {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        moduleFragment.transform(InitMethodTransformer(pluginContext,logger), null)
        moduleFragment.transform(NavInjectMethodTransformer(pluginContext,logger), null)
    }
}
