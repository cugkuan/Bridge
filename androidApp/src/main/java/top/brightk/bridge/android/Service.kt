package top.brightk.bridge.android

import android.util.Log
import android.widget.Toast
import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.core.SUCCEED
import top.brightk.bridge.core.UriRequest
import top.brightk.bridge.core.UriRespond

@CsUrl("kt://app/test")
class Service :CsService {
    override fun call(request: UriRequest): UriRespond {
        Log.e("lmk","服务测试")
        return SUCCEED("")
    }
}