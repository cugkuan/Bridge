package top.brightk.bridge.common

expect class WeakReference<T : Any>(value: T) {
    fun get(): T?
}
