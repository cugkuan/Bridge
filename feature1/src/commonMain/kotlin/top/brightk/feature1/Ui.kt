package top.brightk.feature1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import top.brightk.bridge.annotation.CfUrl
import top.brightk.bridge.annotation.NavUrl
import top.brightk.bridge.call
import top.brightk.bridge.toCfParams


@NavUrl("screen://app/feature1")
@Composable
fun FeatureScreen(controller: NavHostController, backStackEntry: NavBackStackEntry) {

    Box(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {
        Text(
            "这是新的一屏幕,点击去第二屏幕", modifier = Modifier.align(Alignment.Center)
            .clickable {
                controller.navigate("screen://app/feature2")
            }, color = Color.Black, fontSize = 36.sp)

        Text(
            "< 返回上一页", color = Color.Red, fontSize = 26.sp,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 56.dp).clickable {
                controller.popBackStack()
            })

    }
}


@CfUrl("kt://app/view/feature1")
@Composable
fun test1() {
    var number by remember { mutableStateOf(1) }
    var request by remember {
        mutableStateOf("kt://app/view/feature2".toCfParams(number))
    }
    Column {
        Text("这个是Feature1中的界面显示", color = Color.Green)
        Button(onClick = {
            number++
            request = request.clone(number)
        }) {
            println(number)
            Text("点击传递数据:${number}")
        }
        println("这里被重组了===>$number")
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            request.call()
        }

    }
}

