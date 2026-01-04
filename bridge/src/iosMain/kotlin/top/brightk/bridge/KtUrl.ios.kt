package top.brightk.bridge

import platform.Foundation.NSURLComponents
import platform.Foundation.NSURLQueryItem

class IosUrl(url: String) : KtUrl(url) {
    private val components = NSURLComponents(string = url)

    override fun getQueryParameter(key: String): String? {
        val items = components.queryItems ?: return null
        for (item in items) {
            val queryItem = item as NSURLQueryItem
            if (queryItem.name == key) {
                return queryItem.value
            }
        }
        return null
    }
    override fun getScheme(): String? {
        return components.scheme
    }
    override fun getHost(): String? {
        return components.host
    }
    override fun getAuthority(): String? {
        val host = components.host ?: return null
        val port = components.port
        val user = components.user
        val password = components.password
        val sb = StringBuilder()
        if (user != null) {
            sb.append(user)
            if (password != null) {
                sb.append(":").append(password)
            }
            sb.append("@")
        }
        sb.append(host)
        // 处理端口
        if (port != null) {
            sb.append(":").append(port)
        }
        return sb.toString()
    }
    override fun getPath(): String {
        return components.path?:""
    }
}

actual fun getUrl(url: String): KtUrl {
    return IosUrl(url)
}