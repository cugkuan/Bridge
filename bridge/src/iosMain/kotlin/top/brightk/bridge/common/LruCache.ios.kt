import platform.Foundation.NSCache

class IosLruCache<K, V>(maxSize: Int, create: (key: K) -> V?) : LruCache<K, V>(maxSize, create) {
    private val nsCache = NSCache().apply {
        countLimit = maxSize.toULong()
    }

    override fun get(key: K): V? {
        val cachedValue = nsCache.objectForKey(key) as? V
        if (cachedValue != null) {
            return cachedValue
        }
        val newValue = create(key)
        if (newValue != null) {
            nsCache.setObject(newValue, forKey = key)
        }
        return newValue
    }

    fun put(key: K, value: V) {
        nsCache.setObject(value, forKey = key)
    }

    fun remove(key: K) {
        nsCache.removeObjectForKey(key)
    }
}

actual fun <K, V> createLruCache(maxSize: Int, create: (key: K) -> V?): LruCache<K, V> {
    return IosLruCache<K, V>(maxSize, create)
}