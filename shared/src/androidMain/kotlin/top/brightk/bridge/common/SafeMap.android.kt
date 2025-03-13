package top.brightk.bridge.common

import java.util.concurrent.ConcurrentHashMap

class AndroidSafeMap<K ,V>:SafeMap<K,V>{
    private val concurrentHashMap = ConcurrentHashMap<K,V>()
    override fun get(key: K): V? {
        return concurrentHashMap[key]
    }
    override fun put(key: K, value: V) {
        concurrentHashMap[key] = value
    }
    override fun remove(key: K): V? {
        return concurrentHashMap.remove(key)
    }

    override fun getMap(): Map<K, V> {
        return  concurrentHashMap
    }
}

actual fun <K, V> getSafeMap(): SafeMap<K, V> {
    return AndroidSafeMap<K,V>()
}