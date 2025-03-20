package top.brightk.bridge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import top.brightk.bridge.core.CfParams
import top.brightk.bridge.core.CsRequest
import top.brightk.bridge.core.UriRespond

@Composable
fun CfCall(params: CfParams) {
    Bridge.call(params)
}

fun String.toCfParams(data: Any? = null) = CfParams(this).apply {
    this.data = data
}

@Composable
fun String.CfCall() {
    val request by remember {
        mutableStateOf(this.toCfParams())
    }
    Bridge.call(request)
}

@Composable
fun CfParams.call() {
    Bridge.call(this)
}
//Service 相关的操作

fun String.call() = CsRequest(this).call()

fun String.call(action: String) = CsRequest(this).apply {
    this.action = action
}.call()

fun String.call(action: String, vararg params: Pair<String, Any?>): UriRespond? =
    CsRequest(this).apply {
        this.action = action
        params.forEach { value ->
            putParams(value.first, value.second)
        }
    }.call()

fun String.call(vararg params: Pair<String, Any?>): UriRespond? =
    CsRequest(this).apply {
        params.forEach { value ->
            putParams(value.first, value.second)
        }
    }.call()

fun <T> String.get(): T? = CsRequest(url = this).call().let { result ->
    if (result.isSucceed()) {
        result.data as? T
    } else {
        null
    }
}

fun <T> String.get(action: String): T? =
    CsRequest(this).apply { this.action = action }.call().let { result ->
        if (result.isSucceed()) {
            result.data as? T
        } else {
            null
        }
    }

fun <T> String.get(vararg params: Pair<String, Any?>): T? =
    CsRequest(this).apply {
        params.forEach { value ->
            putParams(value.first, value.second)
        }
    }.call().let { result ->
        if (result.isSucceed()) {
            result.data as? T
        } else {
            null
        }
    }

fun <T> String.get(action: String, vararg params: Pair<String, Any?>): T? =
    CsRequest(this).apply {
        this.action = action
        params.forEach { value ->
            putParams(value.first, value.second)
        }
    }.call().let { result ->
        if (result.isSucceed()) {
            result.data as? T
        } else {
            null
        }
    }



