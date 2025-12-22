package top.brightk.bridge.process.ksp.create

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import top.brightk.bridge.annotation.KspBridgeService
import top.brightk.bridge.process.CS_TRANSFER_PACKET
import top.brightk.bridge.process.CsServiceNode
import top.brightk.bridge.process.toTransitContentJson


class CreateCsTransfer(
    private val codeGenerator: CodeGenerator,
    private val csService: List<CsServiceNode>,
) : BaseTransfer() {
    fun create() {
        val className = getCreateName()
        codeGenerator.createNewFile(Dependencies(true), CS_TRANSFER_PACKET, className, "kt")
            .use { stream ->
                with(stream) {
                    appendText("package $CS_TRANSFER_PACKET")
                    newLine(2)
                    appendText("import ${KspBridgeService::class.java.name}")
                    newLine(2)

                    // csService
                    if (csService.isNotEmpty()) {
                        val json = csService.toTransitContentJson()
                        appendText("@KspBridgeService(json = \"${json}\")")
                        newLine(1)
                        appendText("val csService:String? = null")
                        newLine(2)
                    }

                }
            }


    }
}