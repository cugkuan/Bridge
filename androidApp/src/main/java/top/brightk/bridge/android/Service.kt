package top.brightk.bridge.android

import android.util.Log
import androidx.compose.runtime.Composable
import top.brightk.bridge.Bridge
import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.FcUrl
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.core.FcRequest
import top.brightk.bridge.core.SUCCEED
import top.brightk.bridge.core.UriRequest
import top.brightk.bridge.core.UriRespond
import top.brightk.bridge.core.fc.FcManger


@FcUrl("kt://app/call")
@Composable
fun test(request: FcRequest){
}


@CsUrl("kt://app/test")
class Service :CsService {
    override fun call(request: UriRequest): UriRespond {
        Log.e("lmk","服务测试")
        Bridge.init()
        FcManger.registerByUrl("dd",@Composable { request ->
            test(request)
        })

        return SUCCEED("")
    }
}