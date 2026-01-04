package top.brightk.bridge

import androidx.compose.runtime.Composable
import top.brightk.bridge.core.CfParams
import top.brightk.bridge.core.service.CsServiceManger
import top.brightk.bridge.core.Failure
import top.brightk.bridge.core.NotFind
import top.brightk.bridge.core.CsRequest
import top.brightk.bridge.core.UriRespond
import top.brightk.bridge.core.cf.CfManger


fun callService(csRequest: CsRequest): UriRespond {
    val service = CsServiceManger.getService(csRequest.key)
    if (service == null) {
        return NotFind(csRequest)
    } else {
        return try {
            service.call(csRequest)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}

@Composable
fun callFunction(request: CfParams) {
    val f = CfManger.getFunctionByKey(request.key)
    if (f == null && Bridge.isDebug) {
        println("Bridge Debug cf is null key: ${request.key}")
    }
    f?.invoke(request)
}