package top.brightk.feature1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.brightk.bridge.annotation.FcUrl
import top.brightk.bridge.core.FcRequest


@FcUrl("kt://app/view/feature1")
@Composable
fun test1(){
    Column{
        Text("这个是Feature1中的界面显示", color = Color.Green)
        Box(modifier = Modifier.fillMaxWidth().height(50.dp)){
            FcRequest("kt://app/view/feature2").apply {
                putParams(hashMapOf("text" to "这是从Feature1传过来的参数"))
            }
                .call()
        }

    }
}