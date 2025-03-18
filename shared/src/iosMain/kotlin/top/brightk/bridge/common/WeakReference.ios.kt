package top.brightk.bridge.common

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.WeakReference as NativeWeakReference

actual class WeakReference<T : Any> actual constructor(value: T) {
    @OptIn(ExperimentalNativeApi::class)
    private val ref = NativeWeakReference(value)

    @OptIn(ExperimentalNativeApi::class)
    actual fun get(): T? = ref.get()
}