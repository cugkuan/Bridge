package top.brightk.bridge.process.ksp.create

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import top.brightk.bridge.process.CS_TRANSFER_FINIAL
import top.brightk.bridge.process.CS_TRANSFER_FINIAL_CLASS
import top.brightk.bridge.process.CS_TRANSFER_FINIAL_NAV_CLASS
import top.brightk.bridge.process.CsServiceNode
import top.brightk.bridge.process.CfNode
import top.brightk.bridge.process.NavNode


class CreateFinalNavTransfer(
    private val codeGenerator: CodeGenerator,
    private val navNodes: List<NavNode>
) : BaseTransfer() {

    fun create() {
        codeGenerator.createNewFile(
            Dependencies.ALL_FILES,
            CS_TRANSFER_FINIAL, CS_TRANSFER_FINIAL_NAV_CLASS, "kt"
        )
            .use { stream ->
                with(stream) {
                    appendText("package $CS_TRANSFER_FINIAL")
                    newLine(2)
                    appendText("import androidx.compose.runtime.Composable")
                    newLine(1)
                    appendText("import androidx.navigation.NavBackStackEntry")
                    newLine(1)
                    appendText("import androidx.navigation.NavGraphBuilder")
                    newLine(1)
                    appendText("import androidx.navigation.NavHostController")
                    newLine(1)
                    appendText("import androidx.navigation.compose.NavHost")
                    newLine(1)
                    appendText("import androidx.navigation.compose.composable")
                    newLine(1)
                    appendText("import androidx.navigation.compose.rememberNavController")
                    newLine(1)
                    navNodes.forEach { node ->
                        appendText("import ${node.packName}.${node.functionName} as ${node.aliasName}")
                        newLine(1)
                    }
                    newLine(1)
                    // 创建一个新的方法
                    appendText("fun  NavGraphBuilder.navInit(controller: NavHostController){")
                    newLine(2)
                    navNodes.forEach { node ->
                        appendTextWithTab("${node.aliasName}(controller)", 1)
                        newLine(1)
                    }
                    newLine(1)
                    appendText("}")
                    newLine(2)
                }
            }
    }
}