package top.brightk.bridge.core

import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.annotation.Type


data class CsConfig(val type: Type, val create:()->CsService)