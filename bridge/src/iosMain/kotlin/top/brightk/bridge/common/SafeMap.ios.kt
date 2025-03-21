package top.brightk.bridge.common

import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock


actual class SafeMap<K, V> {
    private val map = mutableMapOf<K, V>()
    private val lock = reentrantLock()
    actual  fun get(key: K): V? {
        return lock.withLock {
            map[key]
        }
    }
    actual  fun put(key: K, value: V) {
        lock.withLock {
            map[key] = value
        }
    }
    actual fun remove(key: K): V? {
        return  lock.withLock {
            map.remove(key)
        }
    }
    actual fun getMap(): Map<K, V> {
        return  map
    }

}