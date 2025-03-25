package top.brightk.bridge.kcp

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.UNDEFINED_OFFSET
import org.jetbrains.kotlin.ir.builders.irGet
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.createBlockBody
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.util.Logger

/**
 * 这个的难点是如何向 扩展函数中插入 方法
 */
class NavInjectMethodTransformer(
    private val pluginContext: IrPluginContext,
    private val logger: Logger
) : IrElementTransformerVoid() {

    override fun visitFunction(declaration: IrFunction): IrStatement {
        if (!declaration.hasAnnotation(FqName("top.brightk.bridge.annotation.NavGraphInject"))) {
            return super.visitFunction(declaration)
        }
        if (declaration.valueParameters.isEmpty()){
            logger.error("${declaration.name}需要有NavHostController类型的参数")
            return  super.visitFunction(declaration)
        }
        if (declaration.valueParameters.first().type.classFqName != FqName("androidx.navigation.NavHostController")){
            logger.error("参数类型只能是androidx.navigation.NavHostController")
            return  super.visitFunction(declaration)
        }
        logger.warning("BridgeKcp 找到要注入的方法 ${declaration.name.asString()}")
        val navGraphBuilderClass = pluginContext.referenceFunctions(
            CallableId(
                FqName("com.brightk.bridge"),
                Name.identifier("navInit")
            )
        )
        val initFunctionSymbol =
            navGraphBuilderClass.firstOrNull() ?: return super.visitDeclaration(declaration)
        injectNavInitCall(declaration, initFunctionSymbol)
        logger.warning("BridgeKcp Nav注入成功")
        return super.visitFunction(declaration)
    }

    /**
     * 扩展函数
     */
    private fun injectNavInitCall(declaration: IrFunction, navInitSymbol: IrSimpleFunctionSymbol) {
        val body = declaration.body ?: run {
            val newBody = pluginContext.irFactory.createBlockBody(
                startOffset = UNDEFINED_OFFSET,
                endOffset = UNDEFINED_OFFSET,
                statements = mutableListOf()
            )
            declaration.body = newBody
            newBody
        }
        val irBuilder = DeclarationIrBuilder(pluginContext, declaration.symbol)
        // 使用正确的符号类型构建调用
        val initCall = IrCallImpl(
            startOffset = UNDEFINED_OFFSET,
            endOffset = UNDEFINED_OFFSET,
            type = navInitSymbol.owner.returnType,
            symbol = navInitSymbol,
            typeArgumentsCount = 0,
            origin = IrStatementOrigin.SAFE_CALL
        ).apply {
            extensionReceiver = declaration.extensionReceiverParameter?.let {
                irBuilder.irGet(it)
            }
            putValueArgument(0,irBuilder.irGet(declaration.valueParameters[0]))
        }
        (body as IrBlockBody).statements.add(0, initCall)
    }
}
