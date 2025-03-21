package top.brightk.bridge

import java.net.URI

class AndroidUrl(url: String) : KtUrl(url){
    private val uri = URI(url)
    private var queryParams:HashMap<String,String>? = null
    init {
        val query = uri.query
        if (query.isNullOrEmpty().not()){
            queryParams = HashMap()
            val pairs = query.split("&")
            for (pair in pairs) {
                val keyValue =
                    pair.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val key = keyValue[0]
                val value = if (keyValue.size > 1) keyValue[1] else "" // 处理 `key=` 这种情况
                queryParams!![key] = value
            }
        }
    }
    override fun getQueryParameter(key: String):String? {
       return queryParams?.let {
            it[key]
        }
    }
    override fun getScheme(): String? {
        return  uri.scheme
    }
    override fun getHost(): String {
        return  uri.host
    }
    override fun getAuthority(): String? {
        return uri.authority
    }
    override fun getPath(): String {
        return  uri.path
    }
}
actual fun getUrl(url: String): KtUrl {
    return  AndroidUrl(url)
}