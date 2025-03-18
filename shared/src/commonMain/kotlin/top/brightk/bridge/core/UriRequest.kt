package top.brightk.bridge.core

import top.brightk.bridge.Bridge
import top.brightk.bridge.common.WeakReference
import top.brightk.bridge.getUrl
import top.brightk.bridge.toKey
import kotlin.reflect.KClass
import kotlin.reflect.cast

const val REQUEST_PARAMS_KEY_ACTION: String = "uri_request_key_action"
const val REQUEST_PARAMS_DEFAULT_DATA: String = "uri_request_default_data"

class UriRequest(val url:String,context:Any?=null) {
    private var params: MutableMap<String, Any>? = null

    val uri =  getUrl(url)
    val key = uri.toKey()

    private var ctx:WeakReference<Any>? = null
    init {
        context?.let {
            ctx = WeakReference(context)
        }
    }
    fun setContext(context: Any?){
        context?.let {
            ctx = WeakReference(it)
        }
    }
    fun getContext():Any?{
        return  ctx?.get()
    }

    var action: String?
        get() = getStringParam(REQUEST_PARAMS_KEY_ACTION)
        set(action) {
            action?.let {
                if (params == null) {
                    params = HashMap()
                }
                params!![REQUEST_PARAMS_KEY_ACTION] = it
            }

        }

    var data: Any?
        get() {
            return if (params == null) {
                null
            } else {
                params!![REQUEST_PARAMS_DEFAULT_DATA]
            }
        }
        set(data) {
            if (params == null) {
                params =  HashMap()
            }
            params!![REQUEST_PARAMS_DEFAULT_DATA] = data!!
        }


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

    fun cancel(){

    }

    fun call():UriRespond{
        return Bridge.call(this)
    }
}
