package top.brightk.bridge.core

import LruCache
import createLruCache
import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.Type
import top.brightk.bridge.common.SafeMap
import top.brightk.bridge.common.getSafeMap

object  CsServiceManger {

    private val single:SafeMap<String,CsService> by lazy {
        getSafeMap()
    }
    private val lruCache :LruCache<String,CsService>  = createLruCache(50,{key ->
        val config = ComponentCsServiceManger.getCsConfig(key)
          config?.let { config ->
              if (config.type == Type.DEFAULT){
                  config.create.invoke()
              }else if (config.type == Type.SINGLE){
                  var service = single.get(key)
                  if (service == null) {
                      service = config.create()
                      single.put(key, service)
                  }
                  service
              }else{
                  null
              }
          }
    })

    fun getService(key:String):CsService?{
        val config = ComponentCsServiceManger.getCsConfig(key)
        return if (config == null){
            null
        }else{
            if (config.type == Type.NEW){
                config.create()
            }else{
                lruCache.get(key)
            }
        }
    }
}