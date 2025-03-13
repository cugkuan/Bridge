package top.brightk.bridge

abstract class KtUrl(val url: String) {

    abstract fun getQueryParameter(key: String): String?
    abstract fun getScheme(): String
    abstract fun getHost(): String
    abstract fun getAuthority(): String
    abstract fun getPath(): String
}

expect fun getUrl(url: String): KtUrl