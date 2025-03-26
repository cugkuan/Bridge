package top.brightk.bridge.process

import com.google.devtools.ksp.symbol.KSFunctionDeclaration

/**
 * 考虑到扩展函数同名问题，需要额外处理
 */
fun navProcess(list: List<KSFunctionDeclaration>, log: (msg: String) -> Unit): List<NavNode> {
    val fcList = ArrayList<NavNode>()
    list.forEach { function ->
        if (function.parameters.size > 2) {
            error("最多一个参数，且参数的类型为 NavHostController")
        }
        if (function.parameters.isNotEmpty()) {
            val parameter = function.parameters.first()
            val parameterType = parameter.type.resolve().declaration.qualifiedName
            val fcName = function.qualifiedName?.asString()
            val packName = function.packageName.asString()
            val aliasName = fcName!!.md5()
            log("$packName   ${function.simpleName.asString()}  ${aliasName}")
            if (parameterType?.asString() == "androidx.navigation.NavHostController") {
                fcList.add(NavNode(packName, function.simpleName.asString(), aliasName))
            } else {
                error("参数类型不对，第一个参数类型应该是：androidx.navigation.NavHostController")
            }
        } else {
            error("必须有一个androidx.navigation.NavHostController 类型的参数")
        }
    }
    return fcList
}