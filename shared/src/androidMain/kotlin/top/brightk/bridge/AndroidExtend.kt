package top.brightk.bridge

import android.content.Context
import top.brightk.bridge.core.CsRequest


fun String.call(context: Context) = CsRequest(this).apply {
    setContext(context)
}
    .call()

fun String.call(context: Context, action: String) = CsRequest(this).apply {
    setContext(context)
    this.action = action
}.call()

fun String.call(context: Context, action: String, vararg params: Pair<String, Any?>) =
    CsRequest(this).apply {
        setContext(context)
        this.action = action
        params.forEach { value ->
            putParams(value.first, value.second)
        }
    }.call()

