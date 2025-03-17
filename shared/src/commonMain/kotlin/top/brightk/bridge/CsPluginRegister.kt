package top.brightk.bridge

import top.brightk.bridge.annotation.Type
import top.brightk.bridge.core.service.ComponentCsServiceManger
import top.brightk.bridge.core.service.CsServiceConfig


fun registerService(key:String,type: Type,create:()->CsService){
    ComponentCsServiceManger.register(key, CsServiceConfig(type,create))
}