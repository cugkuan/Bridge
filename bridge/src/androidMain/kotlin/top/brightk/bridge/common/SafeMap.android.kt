package top.brightk.bridge.common

import java.util.concurrent.ConcurrentHashMap

actual class SafeMap<K, V> {
    private val concurrentHashMap = ConcurrentHashMap<K,V>()
    actual fun get(key: K): V? {
        return concurrentHashMap[key]
    }
    actual fun put(key: K, value: V) {
        concurrentHashMap[key] = value
    }
    actual fun remove(key: K): V? {
        return concurrentHashMap.remove(key)
    }
    actual fun getMap(): Map<K, V> {
        return  concurrentHashMap
    }

}