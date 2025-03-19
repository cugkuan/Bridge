package top.brightk.kcp

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.util.Logger

class InitMethodTransformer(
    private val pluginContext: IrPluginContext,
    val logger: Logger
) : IrElementTransformerVoid() {
    override fun visitFunction(declaration: IrFunction): IrStatement {

        if (!declaration.hasAnnotation(FqName("top.brightk.bridge.annotation.Init"))) {
            return super.visitFunction(declaration)
        }
        logger.warning("BridgeKcp 找到要注入的方法 ${declaration.name.asString()}")
        val callableId = CallableId(FqName("com.brightk.bridge"), Name.identifier("init"))
        val initFunctionSymbol = pluginContext.referenceFunctions(callableId)
            .firstOrNull()
            ?: return super.visitFunction(declaration)
        val irBuilder = DeclarationIrBuilder(pluginContext, declaration.symbol)
        val initCall = irBuilder.irCall(initFunctionSymbol)

        val functionBody =
            declaration.body as? IrBlockBodyImpl ?: return super.visitFunction(declaration)
        functionBody.statements.add(0, initCall)
        logger.warning("BridgeKcp 初始化方法注入成功")
        return super.visitFunction(declaration)
    }
}
