package top.brightk.bridge.core

import top.brightk.bridge.KtUrl
import top.brightk.bridge.common.SafeMap
import top.brightk.bridge.common.getSafeMap
import top.brightk.bridge.toKey
import top.brightk.bridge.toKtUrl

object ComponentCsServiceManger {
    private val csServiceConfig: SafeMap<String, CsConfig> = getSafeMap()
    fun registerByUrl(url: String, config: CsConfig) {
       register(url.toKtUrl().toKey(), config)
    }

    fun register(key: String,config: CsConfig){
        csServiceConfig.put(key, config)
    }

    fun getCsConfig(url: String): CsConfig? {
        return getCsConfigByKey(url.toKtUrl().toKey())
    }

    fun getCsConfig(ktUrl: KtUrl): CsConfig? {
        return getCsConfigByKey(ktUrl.toKey())
    }

    fun getCsConfigByKey(key: String): CsConfig? {
        return csServiceConfig.get(key)
    }

}