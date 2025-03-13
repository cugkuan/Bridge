package top.brightk.bridge

import top.brightk.bridge.core.UriRequest
import top.brightk.bridge.core.UriRespond

interface CsService {
    fun call(request: UriRequest):UriRespond
}