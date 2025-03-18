package top.brightk.bridge.core.service

import top.brightk.bridge.KtUrl
import top.brightk.bridge.common.SafeMap
import top.brightk.bridge.toKey
import top.brightk.bridge.toKtUrl

object ComponentCsServiceManger {
    private val csServiceConfig: SafeMap<String, CsServiceConfig> = SafeMap()
    fun registerByUrl(url: String, config: CsServiceConfig) {
       register(url.toKtUrl().toKey(), config)
    }

    fun register(key: String,config: CsServiceConfig){
        csServiceConfig.put(key, config)
    }

    fun getCsConfig(url: String): CsServiceConfig? {
        return getCsConfigByKey(url.toKtUrl().toKey())
    }

    fun getCsConfig(ktUrl: KtUrl): CsServiceConfig? {
        return getCsConfigByKey(ktUrl.toKey())
    }

    fun getCsConfigByKey(key: String): CsServiceConfig? {
        return csServiceConfig.get(key)
    }

}