package top.brightk.bridge.process.ksp.create

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import top.brightk.bridge.process.CS_TRANSFER_FINIAL
import top.brightk.bridge.process.CS_TRANSFER_FINIAL_CLASS
import top.brightk.bridge.process.CsServiceNode
import top.brightk.bridge.process.FcNode


class CreateFinalTransfer(
    private val codeGenerator: CodeGenerator,
    private val csService: List<CsServiceNode>,
    private val fcList:List<FcNode>
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
                    newLine(1)
                    appendText("import top.brightk.bridge.registerFunction")
                    newLine(1)
                    appendText("import androidx.compose.runtime.Composable")
                    newLine(2)
                    newLine(1)
                    appendText("fun  init(){")
                    newLine(1)
                    //cs
                    if (csService.isNotEmpty()) {
                        csService.forEach { service ->
                            appendTextWithTab(
                                "registerService(\"${service.key}\",${service.type.name},{ ${service.className}() });",
                                1
                            )
                            newLine(1)
                        }
                        newLine(1)
                    }
                    if (fcList.isNotEmpty()) {
                        fcList.forEach{ fc ->
                            if (fc.hasParams) {
                                appendTextWithTab("registerFunction(\"${fc.key}\", @Composable {",1)
                                appendTextWithTab("\n",1)
                                appendTextWithTab(" ${fc.functionName}(it) ",2)
                                appendTextWithTab("\n",1)
                                appendTextWithTab("Unit",2)
                                appendTextWithTab("\n",2)
                                appendTextWithTab("})",1)
                            }else{
                                appendTextWithTab("registerFunction(\"${fc.key}\", @Composable {",1)
                                appendTextWithTab("\n",1)
                                appendTextWithTab(" ${fc.functionName}() ",2)
                                appendTextWithTab("\n",1)
                                appendTextWithTab("Unit",2)
                                appendTextWithTab("\n",2)
                                appendTextWithTab("})",1)
                            }
                            newLine(1)
                        }
                        newLine(1)
                    }
                    newLine(1)
                    appendText("}")
                    newLine(2)

                }
            }
    }
}