
abstract class LruCache<K,V>(val maxSize:Int,val create:(key:K)->V?) {
    abstract fun get(key: K): V?
}

expect fun<K,V> createLruCache( maxSize:Int, create:(key:K)->V?):LruCache<K,V>