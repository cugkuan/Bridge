package top.brightk.bridge

import top.brightk.bridge.core.CsServiceManger
import top.brightk.bridge.core.FIALURE
import top.brightk.bridge.core.NOTFIND
import top.brightk.bridge.core.UriRequest
import top.brightk.bridge.core.UriRespond


fun callService(uriRequest: UriRequest): UriRespond {
    val service = CsServiceManger.getService(uriRequest.key)
    return  try {
          service?.call(uriRequest)?:NOTFIND(uriRequest)
    }catch (e:Exception){
          FIALURE(e)
    }
}