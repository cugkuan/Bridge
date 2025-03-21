

import platform.Foundation.NSCache

class IosLruCache<K,V>(maxSize:Int,create:(key:K)->V?) : LruCache<K, V>(maxSize,create) {
    private val cache = NSCache().apply {
        countLimit = maxSize.toLong().toULong()
    }
    override fun get(key: K): V? {
        var  value = cache.objectForKey(key)
        if (value == null){
            value = create(key)
            cache.setObject(value,key)
        }
        return  value as? V
    }
}
actual fun <K, V> createLruCache(maxSize: Int, create: (key: K) -> V?): LruCache<K, V> {
    return  IosLruCache<K,V>(maxSize,create)
}