package top.brightk.bridge.core.fc

import androidx.compose.runtime.Composable
import top.brightk.bridge.KtUrl
import top.brightk.bridge.common.SafeMap
import top.brightk.bridge.core.FcRequest
import top.brightk.bridge.toKey
import top.brightk.bridge.toKtUrl

object FcManger {
    private val fcConfig: SafeMap<String, @Composable (request:FcRequest)->Unit> = SafeMap()
    fun registerByUrl(url: String,f:@Composable (request:FcRequest)->Unit) {
       register(url.toKtUrl().toKey(),f)
    }

    fun register(key: String, f:@Composable (request:FcRequest)->Unit){
        fcConfig.put(key, f)
    }

    fun getFunction(url: String): (@Composable (request:FcRequest)->Unit)?  {
        return getFunctionByKey(url.toKtUrl().toKey())
    }

    fun getFunction(ktUrl: KtUrl): (@Composable (request:FcRequest)->Unit)?  {
        return getFunctionByKey(ktUrl.toKey())
    }

    fun getFunctionByKey(key: String): (@Composable (request:FcRequest)->Unit)? {
        return fcConfig.get(key)
    }

}