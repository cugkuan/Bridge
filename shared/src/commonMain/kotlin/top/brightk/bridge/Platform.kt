package top.brightk.bridge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform