package top.brightk.bridge

import top.brightk.bridge.annotation.Type
import top.brightk.bridge.core.ComponentCsServiceManger
import top.brightk.bridge.core.CsConfig


fun registerService(key:String,type: Type,create:()->CsService){
    ComponentCsServiceManger.register(key, CsConfig(type,create))
}