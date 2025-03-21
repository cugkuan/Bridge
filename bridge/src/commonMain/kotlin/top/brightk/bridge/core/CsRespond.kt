package top.brightk.bridge.core

import top.brightk.bridge.CS_CODE_NOT_FIND
import top.brightk.bridge.CS_CODE_SERVICE_LACK_PARAMS
import top.brightk.bridge.CS_CODE_SUCCEED

sealed  class UriRespond(val code:Int,val data:Any?= null,throwable: Throwable?= null) {
    fun isSucceed() = (code == CS_CODE_SUCCEED)
}
class Succeed(data: Any? = null):UriRespond(CS_CODE_SUCCEED,data)
class Failure(e:Throwable):UriRespond(CS_CODE_NOT_FIND)
class NotFind(request: CsRequest):UriRespond(CS_CODE_NOT_FIND,request.url)
class LackParams(msg:String):UriRespond(CS_CODE_SERVICE_LACK_PARAMS,throwable = CsException("缺乏参数"))
class Cancel :UriRespond(CS_CODE_SUCCEED)