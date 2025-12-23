package top.brightk.bridge.process.ksp.create

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import top.brightk.bridge.annotation.KspBridgeCf
import top.brightk.bridge.process.CS_TRANSFER_PACKET
import top.brightk.bridge.process.CfNode
import top.brightk.bridge.process.NAV_URL_BRIDGE
import top.brightk.bridge.process.NavNode
import top.brightk.bridge.process.toTransitContentJson


class CreateNavTransfer(
    private val codeGenerator: CodeGenerator,
    private val fcList: List<NavNode>,
) : BaseTransfer() {
    fun create() {
        val className = getCreateName()
        codeGenerator.createNewFile(Dependencies(false), CS_TRANSFER_PACKET, className, "kt")
            .use { stream ->
                with(stream) {
                    appendText("package $CS_TRANSFER_PACKET")
                    newLine(2)
                    appendText("import $NAV_URL_BRIDGE")
                    newLine(2)
                    // fc
                    if (fcList.isNotEmpty()) {
                        val json = fcList.toTransitContentJson()
                        appendText("@KspBridgeNav(json = \"${json}\")")
                        newLine(1)
                        appendText("val navConfig:String? = null")
                        newLine(2)
                    }

                }
            }


    }
}