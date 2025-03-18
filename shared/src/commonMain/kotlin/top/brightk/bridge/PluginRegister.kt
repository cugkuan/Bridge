package top.brightk.bridge

import androidx.compose.runtime.Composable
import top.brightk.bridge.annotation.Type
import top.brightk.bridge.core.FcRequest
import top.brightk.bridge.core.fc.FcManger
import top.brightk.bridge.core.service.ComponentCsServiceManger
import top.brightk.bridge.core.service.CsServiceConfig


fun registerService(key:String,type: Type,create:()->CsService){
    ComponentCsServiceManger.register(key, CsServiceConfig(type,create))
}
fun registerFunction(key: String,call:@Composable (request: FcRequest)->Unit){
    FcManger.register(key,call)
}