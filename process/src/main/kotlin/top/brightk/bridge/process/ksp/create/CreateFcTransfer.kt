package top.brightk.bridge.process.ksp.create

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import top.brightk.bridge.annotation.KspBridgeCf
import top.brightk.bridge.process.CS_TRANSFER_PACKET
import top.brightk.bridge.process.CfNode
import top.brightk.bridge.process.toTransitContentJson


class CreateFcTransfer(
    private val codeGenerator: CodeGenerator,
    private val fcList: List<CfNode>,
) : BaseTransfer() {
    fun create() {
        val className = getCreateName()
        codeGenerator.createNewFile(Dependencies(false), CS_TRANSFER_PACKET, className, "kt")
            .use { stream ->
                with(stream) {
                    appendText("package $CS_TRANSFER_PACKET")
                    newLine(2)
                    appendText("import ${KspBridgeCf::class.java.name}")
                    newLine(2)
                    // fc
                    if (fcList.isNotEmpty()) {
                        val json = fcList.toTransitContentJson()
                        appendText("@KspBridgeCf(json = \"${json}\")")
                        newLine(1)
                        appendText("val fc:String? = null")
                        newLine(2)
                    }

                }
            }


    }
}