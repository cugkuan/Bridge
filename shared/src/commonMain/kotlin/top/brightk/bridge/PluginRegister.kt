package top.brightk.bridge

import androidx.compose.runtime.Composable
import top.brightk.bridge.annotation.Type
import top.brightk.bridge.core.CfParams
import top.brightk.bridge.core.cf.CfManger
import top.brightk.bridge.core.service.ComponentCsServiceManger
import top.brightk.bridge.core.service.CsServiceConfig

/**
 * 供ksp插件使用。
 */

fun registerService(key:String,type: Type,create:()->CsService){
    ComponentCsServiceManger.register(key, CsServiceConfig(type,create))
}

/**
 * Cf- Compose function
 */
fun registerCf(key: String, call:@Composable (request: CfParams)->Unit){
    CfManger.register(key,call)
}