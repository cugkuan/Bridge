

class AndroidLruCache<K,V>(maxSize:Int,create:(key:K)->V?) : LruCache<K, V>(maxSize,create) {
    private val lruCache  = object :android.util.LruCache<K,V>(maxSize){
        override fun create(key: K): V? {
            return create(key)
        }
    }
    override fun get(key: K): V? {
        return  lruCache.get(key)
    }
}

actual fun <K, V> createLruCache(maxSize: Int, create: (key: K) -> V?): LruCache<K, V> {
    return AndroidLruCache<K,V>(maxSize,create)
}