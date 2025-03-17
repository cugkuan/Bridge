package top.brightk.kcp


import org.jetbrains.kotlin.backend.common.extensions.FirIncompatiblePluginAPI
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptChildrenVoid
import org.jetbrains.kotlin.ir.visitors.acceptVoid
import org.jetbrains.kotlin.name.FqName


class BridgeTransformer(private val pluginContext: IrPluginContext) : IrElementTransformerVoid() {
    @OptIn(FirIncompatiblePluginAPI::class, UnsafeDuringIrConstructionAPI::class)
    override fun visitClass(declaration: IrClass): IrStatement {
        // 检查类名是否为 "Bridge"
        if (declaration.name.asString() == "Bridge") {
            declaration.acceptVoid(object : IrElementVisitorVoid {
                override fun visitFunction(declaration: IrFunction) {
                    // 检查方法名是否为 "init"
                    if (declaration.name.asString() == "<init>") {
                        // 插入 CsServiceInit.init() 调用
                        val csServiceInitClass = pluginContext.referenceClass(
                            FqName("CsServiceInit")
                        ) ?: return

                        val initFunction = csServiceInitClass.owner.declarations.filterIsInstance<IrFunction>()
                            .firstOrNull { it.name.asString() == "init" } ?: return

                        if (initFunction is IrSimpleFunction) {

                        }
                    }
                }
            })
        }

        return super.visitClass(declaration)
    }
}