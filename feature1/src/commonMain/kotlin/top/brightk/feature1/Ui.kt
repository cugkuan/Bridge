package top.brightk.feature1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.brightk.bridge.Bridge
import top.brightk.bridge.annotation.CfUrl
import top.brightk.bridge.core.CfParams


@CfUrl("kt://app/view/feature1")
@Composable
fun test1(){
    var number by remember { mutableStateOf(1) }
    var request  by remember {
        mutableStateOf( CfParams("kt://app/view/feature2").apply {
            data = number
        })
    }
    Column{
        Text("这个是Feature1中的界面显示", color = Color.Green)
        Button(onClick = {
            number++
            request = request.clone(number)
        }){
            println(number)
            Text("点击传递数据:${number}")
        }
        println("这里被重组了===>$number")
        Box(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(50.dp)){
             Bridge.call(request)
        }

    }
}
