package top.brightk.bridge.core.cf

import androidx.compose.runtime.Composable
import top.brightk.bridge.KtUrl
import top.brightk.bridge.common.SafeMap
import top.brightk.bridge.core.CfParams
import top.brightk.bridge.toKey
import top.brightk.bridge.toKtUrl

object CfManger {
    private val cfConfig: SafeMap<String, @Composable (request:CfParams)->Unit> = SafeMap()
    fun registerByUrl(url: String,f:@Composable (request:CfParams)->Unit) {
       register(url.toKtUrl().toKey(),f)
    }

    fun register(key: String, f:@Composable (request:CfParams)->Unit){
        cfConfig.put(key, f)
    }

    fun getFunction(url: String): (@Composable (request:CfParams)->Unit)?  {
        return getFunctionByKey(url.toKtUrl().toKey())
    }

    fun getFunction(ktUrl: KtUrl): (@Composable (request:CfParams)->Unit)?  {
        return getFunctionByKey(ktUrl.toKey())
    }

    fun getFunctionByKey(key: String): (@Composable (request:CfParams)->Unit)? {
        return cfConfig.get(key)
    }

}