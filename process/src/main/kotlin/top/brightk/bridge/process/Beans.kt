package top.brightk.bridge.process

import top.brightk.bridge.annotation.Type

data class CsServiceNode(
    val className: String,
    val key: String,
    val type: Type
)

data class FcNode(
    val functionName:String,
    val key: String,
    val hasParams:Boolean
)