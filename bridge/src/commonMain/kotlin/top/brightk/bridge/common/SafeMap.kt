package top.brightk.bridge.common

expect class  SafeMap<K,V>(){
    fun get(key:K):V?
    fun put(key: K,value:V)
    fun remove(key: K):V?

    fun getMap():Map<K,V>

}
