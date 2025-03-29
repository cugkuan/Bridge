package top.brightk.feature2

import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.core.CsRequest
import top.brightk.bridge.core.Succeed
import top.brightk.bridge.core.UriRespond

@CsUrl("bridge://app/feature2/test1")
class Feature2Service :CsService {
    override fun call(request: CsRequest): UriRespond {
        val value = request.getStringParam("info")
        println( "action:${request.action} : $value :$value")
        return Succeed()
    }
}
@CsUrl("bridge://app/feature2/test2")
class Feature3Service:CsService {
    override fun call(request: CsRequest): UriRespond {
        val count:Int = request.getIntParam("count")
        return Succeed(count+1)
    }
}