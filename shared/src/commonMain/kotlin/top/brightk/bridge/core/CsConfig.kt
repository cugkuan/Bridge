package top.brightk.bridge.core

import top.brightk.bridge.CsService

data class CsConfig(val type: Type, val create:()->CsService)