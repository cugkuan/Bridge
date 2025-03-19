package top.brightk.feature1

import android.content.Context
import android.widget.Toast
import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.core.SUCCEED
import top.brightk.bridge.core.UriRequest
import top.brightk.bridge.core.UriRespond

actual fun platform() = "Android"



@CsUrl("bridge://app/featureAndroid")
class Feature1AndroidCsService: CsService {
    override fun call(request: UriRequest): UriRespond {
        (request.getContext() as? Context)?.let {
            Toast.makeText(it,"服务连接测试",Toast.LENGTH_LONG).show()
        }
        return SUCCEED("")
    }


}