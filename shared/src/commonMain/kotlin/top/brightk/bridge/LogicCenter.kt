package top.brightk.bridge

import androidx.compose.runtime.Composable
import top.brightk.bridge.core.FcRequest
import top.brightk.bridge.core.service.CsServiceManger
import top.brightk.bridge.core.FIALURE
import top.brightk.bridge.core.NOTFIND
import top.brightk.bridge.core.UriRequest
import top.brightk.bridge.core.UriRespond
import top.brightk.bridge.core.fc.FcManger


fun callService(uriRequest: UriRequest): UriRespond {
    val service = CsServiceManger.getService(uriRequest.key)
    return  try {
          service?.call(uriRequest)?:NOTFIND(uriRequest)
    }catch (e:Exception){
          FIALURE(e)
    }
}

@Composable
fun callFunction(request: FcRequest){
    val f = FcManger.getFunctionByKey(request.key)
    f?.invoke(request)
}