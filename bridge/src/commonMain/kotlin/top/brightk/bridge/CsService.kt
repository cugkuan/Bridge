package top.brightk.bridge

import top.brightk.bridge.core.CsRequest
import top.brightk.bridge.core.UriRespond

interface CsService {
    fun call(request: CsRequest):UriRespond
}