package top.brightk.bridge.process.ksp.create

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import top.brightk.bridge.process.CS_TRANSFER_FINIAL
import top.brightk.bridge.process.CS_TRANSFER_FINIAL_CLASS
import top.brightk.bridge.process.CsServiceNode


class CreateFinalTransfer(
    private val codeGenerator: CodeGenerator,
    private val csService: List<CsServiceNode>,
) : BaseTransfer() {

    fun create() {
        codeGenerator.createNewFile(
            Dependencies(false),
            CS_TRANSFER_FINIAL, CS_TRANSFER_FINIAL_CLASS, "kt"
        )
            .use { stream ->
                with(stream) {
                    //import com.brightk.cs.CsPluginRegister
                    appendText("package $CS_TRANSFER_FINIAL")
                    newLine(2)
                    appendText("import top.brightk.bridge.registerService")
                    newLine(1)
                    appendText("import top.brightk.bridge.annotation.Type.DEFAULT")
                    newLine(1)
                    appendText("import top.brightk.bridge.annotation.Type.NEW")
                    newLine(1)
                    appendText("import top.brightk.bridge.annotation.Type.SINGLE")
                    newLine(2)
                    appendText("class $CS_TRANSFER_FINIAL_CLASS {")
                    newLine(1)
                    appendTextWithTab("fun  init(){")
                    newLine(1)
                    //cs
                    if (csService.isNotEmpty()) {
                        csService.forEach { service ->
                            appendTextWithTab(
                                "registerService(\"${service.key}\",${service.type.name},{ ${service.className}() });",
                                2
                            )
                            newLine(1)
                        }
                        newLine(1)
                    }
                    newLine(1)
                    appendTextWithTab("}")
                    newLine(2)
                    appendText("}")

                }
            }
    }
}