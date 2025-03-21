package top.brightk.bridge.core

import top.brightk.bridge.getUrl
import top.brightk.bridge.toKey

/**
 * @Compose 跨组件调用的参数定义
 */
open  class CfParams(val url: String) {
    val ktUrl = getUrl(url)
    val key = ktUrl.toKey()
    open var data: Any? = null

    fun clone(data:Any?):CfParams{
        return  CfParams(url).apply {
            this.data = data
        }
    }
}
