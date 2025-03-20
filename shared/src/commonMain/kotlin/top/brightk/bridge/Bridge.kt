package top.brightk.bridge

import androidx.compose.runtime.Composable
import top.brightk.bridge.core.CfParams
import top.brightk.bridge.core.CsRequest
import top.brightk.bridge.core.UriRespond

const val CS_CODE_SUCCEED = 200
/**
 * 用户没有指定 OnRequestServiceListener 的时候，默认回调
 */
const val CS_CODE_DEFAULT = 201

const val CS_CDE_FAILURE = 300

const val CS_CODE_NOT_FIND = 404
/**
 * 服务器内部错误
 */
const val CS_CODE_SERVICE_ERROR = 500
const val CS_CODE_SERVICE_TIMEOUT = 501

const val CS_CODE_RESPOND_NULL = 502
/**
 * context 缺失了
 */
const val CS_CODE_SERVICE_CONTEXT_OUT = 501
/**
 * 缺少参数
 */
const val CS_CODE_SERVICE_LACK_PARAMS = 502

const val CS_CODE_SERVICE_CANCEL_FAILURE = 600

/**
 * 拦截器
 */
const val CS_CODE_INTERCEPTOR_FAILURE = 700
fun KtUrl.toKey():String{
    return StringBuilder().append(getScheme().orEmpty())
        .append("-")
        .append(getAuthority().orEmpty())
        .append("-")
        .append(getPath())
        .toString()
}
fun String.toKtUrl() = getUrl(this)
object Bridge {
    fun init(){
    }
    fun call(request: CsRequest): UriRespond {
        return callService(request)
    }
    @Composable
    fun call(params: CfParams){
        callFunction(params)
    }

}