package top.brightk.bridge.process.ksp.create

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import top.brightk.bridge.annotation.KspBridgeService
import top.brightk.bridge.process.CS_TRANSFER_PACKET
import top.brightk.bridge.process.InitNode
import top.brightk.bridge.process.toTransitContentJson

/**
 * 由于 ksp 的工作原理，决定
 */
class CreateInitTransfer(
    private val codeGenerator: CodeGenerator,
    private val node: InitNode
) : BaseTransfer() {

    fun create() {
        val className = getCreateName()
        codeGenerator.createNewFile(
            Dependencies(false),
            CS_TRANSFER_PACKET, className, "kt"
        )
            .use { stream ->
                with(stream) {
                    appendText("package ${top.brightk.bridge.process.CS_TRANSFER_PACKET}")
                    newLine(2)
                    appendText("import ${top.brightk.bridge.annotation.KspBridgeInit::class.java.name}")
                    newLine(2)


                    appendText("@KspBridgeInit(className = \"${node.functionName}\")")
                    newLine(1)
                    appendText("val initFunction:String? = null")
                    newLine(2)

                }
            }
    }
}