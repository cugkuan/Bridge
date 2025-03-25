package top.brightk.bridge.process

import top.brightk.bridge.annotation.Type

data class CsServiceNode(
    val className: String,
    val key: String,
    val type: Type
)

data class CfNode(
    val functionName:String,
    val key: String,
    val hasParams:Boolean
)

data class FunNode(val functionName: String)

data class NavNode(
    val functionName: String,
    val url:String,
    val hasParams: Boolean
)