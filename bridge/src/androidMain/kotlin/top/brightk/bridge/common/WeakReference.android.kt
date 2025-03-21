package top.brightk.bridge.common

import java.lang.ref.WeakReference as JavaWeakReference

actual class WeakReference<T : Any> actual constructor(value: T) {
    private val ref = JavaWeakReference(value)
    actual fun get(): T? = ref.get()
}