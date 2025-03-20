package top.brightk.bridge.core

import top.brightk.bridge.Bridge
import top.brightk.bridge.common.WeakReference
import top.brightk.bridge.getUrl
import top.brightk.bridge.toKey
import kotlin.reflect.KClass
import kotlin.reflect.cast

const val REQUEST_PARAMS_KEY_ACTION: String = "uri_request_key_action"
const val REQUEST_PARAMS_DEFAULT_DATA: String = "uri_request_default_data"

class CsRequest(val url: String, context: Any? = null) {
    private var params: MutableMap<String, Any?>? = null

    val uri = getUrl(url)
    val key = uri.toKey()

    private var ctx: WeakReference<Any>? = null

    init {
        context?.let {
            ctx = WeakReference(context)
        }
    }

    fun setContext(context: Any?) {
        context?.let {
            ctx = WeakReference(it)
        }
    }

    fun getContext(): Any? {
        return ctx?.get()
    }

    var action: String?
        get() = getStringParam(REQUEST_PARAMS_KEY_ACTION)
        set(action) {
            action?.let {
                this.params = (this.params?:HashMap()).apply {
                    this[REQUEST_PARAMS_KEY_ACTION] = it
                }
            }

        }

    var data: Any?
        get() = params?.get(REQUEST_PARAMS_DEFAULT_DATA)
        set(value) {
            this.params = (this.params ?: HashMap()).apply {
                this[REQUEST_PARAMS_DEFAULT_DATA] = value
            }
        }

    fun putParams(params: MutableMap<String, Any>) {
        this.params = (this.params ?: HashMap()).apply {
            putAll(params)
        }
    }

    fun putParams(key: String, value: Any?) {
        this.params = (this.params ?: HashMap()).apply {
            this[key] = value
        }
    }

    fun getParams(): Map<String, Any?>? {
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
        return  params?.get(key)
    }

    fun <T : Any> getParam(clazz: KClass<T>, key: String): T? {
        return  params?.get(key)?.let { field ->
            if (clazz.isInstance(field)) {
                clazz.cast(field)
            } else {
                null
            }
        }
    }


    fun call(): UriRespond {
        return Bridge.call(this)
    }
}
