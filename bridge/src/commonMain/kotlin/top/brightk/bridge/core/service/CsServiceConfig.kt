package top.brightk.bridge.core.service

import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.Type


data class CsServiceConfig(val type: Type, val create:()->CsService)