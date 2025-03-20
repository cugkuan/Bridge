package top.brightk.bridge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import top.brightk.bridge.core.CfParams

@Composable
fun CfCall(params: CfParams){
    Bridge.call(params)
}
fun String.toCfParams(data: Any?= null) = CfParams(this).apply {
    this.data = data
}
@Composable
fun String.CfCall(){
    val request by remember {
        mutableStateOf(this.toCfParams())
    }
    Bridge.call(request)
}

@Composable
fun CfParams.call(){
    Bridge.call(this)
}

