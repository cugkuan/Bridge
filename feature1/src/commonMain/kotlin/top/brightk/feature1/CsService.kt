package top.brightk.feature1

import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.core.SUCCEED
import top.brightk.bridge.core.UriRequest
import top.brightk.bridge.core.UriRespond

@CsUrl("bridge://app/feature1")
class Feature1CsService: CsService {
    override fun call(request: UriRequest): UriRespond {

        return SUCCEED("")
    }


}