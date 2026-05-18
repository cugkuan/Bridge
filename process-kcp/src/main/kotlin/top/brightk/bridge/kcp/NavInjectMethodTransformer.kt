package top.brightk.bridge.kcp

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.UNDEFINED_OFFSET
import org.jetbrains.kotlin.ir.builders.irGet
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.createBlockBody
import org.jetbrains.kotlin.ir.declarations.IrParameterKind
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.util.Logger

class NavInjectMethodTransformer(
    private val pluginContext: IrPluginContext,
    private val logger: Logger
) : IrElementTransformerVoid() {

    override fun visitFunction(declaration: IrFunction): IrStatement {
        if (!declaration.hasAnnotation(FqName("top.brightk.bridge.annotation.NavGraphInject"))) {
            return super.visitFunction(declaration)
        }
        val valueParameters = declaration.parameters.filter { it.kind == IrParameterKind.Regular }
        if (valueParameters.isEmpty()){
            logger.error("${declaration.name}需要有NavHostController类型的参数")
            return  super.visitFunction(declaration)
        }
        if (valueParameters.first().type.classFqName != FqName("androidx.navigation.NavHostController")){
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

    @OptIn(UnsafeDuringIrConstructionAPI::class)
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
        val initCall = irBuilder.irCall(navInitSymbol).apply {
            val targetParams = navInitSymbol.owner.parameters
            targetParams.forEachIndexed { index, param ->
                when (param.kind) {
                    IrParameterKind.ExtensionReceiver -> {
                        this.arguments[index] = declaration.parameters.firstOrNull { it.kind == IrParameterKind.ExtensionReceiver }?.let {
                            irBuilder.irGet(it)
                        }
                    }
                    IrParameterKind.Regular -> {
                        val valueParameters = declaration.parameters.filter { it.kind == IrParameterKind.Regular }
                        if (valueParameters.isNotEmpty()) {
                            this.arguments[index] = irBuilder.irGet(valueParameters[0])
                        }
                    }
                    else -> {}
                }
            }
        }
        (body as IrBlockBody).statements.add(0, initCall)
        logger.log("Init  方法注入成功")
    }
}
