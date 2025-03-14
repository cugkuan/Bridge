package top.brightk.bridge.process

import top.brightk.bridge.process.annotation.Type

data class CsServiceNode(
    val className: String,
    val key: String,
    val type: Type
)