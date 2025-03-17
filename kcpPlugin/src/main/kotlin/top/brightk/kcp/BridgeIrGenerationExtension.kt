package top.brightk.kcp;

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.util.functions
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid
import org.jetbrains.kotlin.util.Logger


class BridgeIrGenerationExtension(val logger: Logger) : IrGenerationExtension {
    init {
        logger.warning("注册成功")
    }
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {

        moduleFragment.accept( object :IrElementTransformerVoid(){
            init {
                logger.warning("证明我来过")
            }
            override fun visitClass(declaration: IrClass): IrStatement {
                logger.warning("玛蒂娜"+declaration.name.asString())
                return super.visitClass(declaration)
            }
        },null)
        logger.warning("文件的遍历")

        moduleFragment.files.forEach { file ->
            logger.warning(file.name)
            file.declarations.forEach { declaration ->
                logger.warning(file.name)
                if (declaration is IrClass && declaration.name.asString() == "CsService") {
                    // addTestMethod(declaration, pluginContext)
                }
            }
        }
        logger.warning("注册》》》》》》》》》")
        moduleFragment.transformChildren(
            object : IrElementTransformerVoid() {
                override fun visitClass(declaration: IrClass): IrStatement {
                    logger.warning(declaration.name.asString())
                    return super.visitClass(declaration)
                }

                override fun visitFunction(declaration: IrFunction): IrStatement {
                    return super.visitFunction(declaration)
                    logger.warning(declaration.name.asString())
                }
            },
            data = null
        )

    }
}