package top.brightk.bridge.core

import top.brightk.bridge.getUrl
import top.brightk.bridge.toKey
import kotlin.reflect.KClass
import kotlin.reflect.cast

class FcRequest(val url:String) {
    private var params: MutableMap<String, Any>? = null

    val uri =  getUrl(url)
    val key = uri.toKey()

    fun putParams(params: MutableMap<String, Any>) {
        if (this.params == null) {
            this.params = params
        } else {
            this.params!!.putAll(params)
        }
    }
    fun getParams(): Map<String, Any>? {
        return params
    }
    fun getQueryParameter(key: String): String? {
        return uri.getQueryParameter(key)
    }
    fun getStringParam(key: String): String? {
        return getParam<String>(String::class, key)!!
    }

    fun getIntParam(key: String): Int {
        return getParam<Int>(Int::class, key)!!
    }
    fun getLongParam(key: String): Long {
        return getParam<Long>(Long::class, key)!!
    }
    fun getBooleanParam(key: String): Boolean {
        return getParam<Boolean>(Boolean::class, key)!!
    }
    fun getParam(key: String): Any? {
        return if (params != null) {
            params!![key]
        } else {
            null
        }
    }
    fun <T : Any> getParam(clazz: KClass<T>, key: String): T? {
        if (params == null) {
            return null
        }
        val field = params!![key]
        if (field != null) {
            return if ( clazz.isInstance( field)){
                clazz.cast(field)
            }else{
                null
            }
        }
        return null
    }
}
