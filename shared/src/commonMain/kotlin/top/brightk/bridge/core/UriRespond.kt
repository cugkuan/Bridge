package top.brightk.bridge.core

import top.brightk.bridge.CS_CODE_NOT_FIND
import top.brightk.bridge.CS_CODE_SERVICE_LACK_PARAMS
import top.brightk.bridge.CS_CODE_SUCCEED

sealed  class UriRespond(val code:Int,val data:Any?= null,throwable: Throwable?= null) {
    fun isSucceed() = (code == 0)
}
class SUCCEED(data: Any?):UriRespond(CS_CODE_SUCCEED,data)
class FIALURE(e:Throwable):UriRespond(CS_CODE_NOT_FIND)
class NOTFIND(request: UriRequest):UriRespond(CS_CODE_NOT_FIND,request.url)
class LACKPARAMS(msg:String):UriRespond(CS_CODE_SERVICE_LACK_PARAMS,throwable = CsException("缺乏参数"))
class CANCEL():UriRespond(CS_CODE_SUCCEED)