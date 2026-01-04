package top.brightk.feature1

import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.annotation.Type
import top.brightk.bridge.core.Succeed
import top.brightk.bridge.core.CsRequest
import top.brightk.bridge.core.UriRespond

@CsUrl("bridge://app/feature1", type = Type.NEW)
class Feature1CsService: CsService {
    override fun call(request: CsRequest): UriRespond {

        return Succeed("")
    }


}