package top.brightk.bridge.process

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import top.brightk.bridge.annotation.NavUrl


@OptIn(KspExperimental::class)
fun navProcess(list: List<KSFunctionDeclaration>, log: (msg: String) -> Unit): List<NavNode> {
    val fcList = ArrayList<NavNode>()
    list.forEach { function ->
        if (function.annotations.any { it.shortName.asString() == "Composable" }) {
            log(function.simpleName.asString())
            if (function.parameters.size > 3) {
                error("最多二个参数，且参数的类型为 NavHostController 和  NavBackStackEntry")
            }
            if (function.parameters.isNotEmpty()) {
                val parameter = function.parameters.first()
                val parameterType = parameter.type.resolve().declaration.qualifiedName
                val fcName = function.qualifiedName?.asString()
                val url = function.getAnnotationsByType(NavUrl::class).first().uri
                log(function.qualifiedName?.asString().orEmpty())
                if (parameterType?.asString() == "androidx.navigation.NavHostController") {
                    if (function.parameters.size == 2) {
                        val secondParameter = function.parameters[1].type.resolve().declaration.qualifiedName?.asString()
                        if (secondParameter == "androidx.navigation.NavBackStackEntry") {
                            fcList.add(NavNode(fcName!!,url,true))
                        } else {
                            error("参数类型只能是NavBackStackEntry")
                        }
                    } else {
                        fcList.add(NavNode(fcName!!, url, false))
                    }
                } else {
                    error("参数类型不对，第一个参数类型应该是：androidx.navigation.NavHostController")
                }
            } else {
                error("必须有一个androidx.navigation.NavHostController 类型的参数")
            }
        } else {
            error("${function.simpleName.asString()}没有被@Composable修饰")
        }
    }
    return fcList
}