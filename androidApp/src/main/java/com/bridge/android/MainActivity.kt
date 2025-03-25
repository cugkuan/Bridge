package com.bridge.android

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import top.brightk.bridge.CfCall
import top.brightk.bridge.Greeting
import top.brightk.bridge.call
import top.brightk.bridge.get
import top.brightk.bridge.toCfParams
import top.brightk.feature1.TestViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInject()
        setContent {
            MyNavHost()
        }
    }
}
@Composable
fun GreetingView(text: String,vm:TestViewModel = TestViewModel(),navHostController: NavHostController ) {
    val ctx = LocalContext.current
    Column {
        Text(text = "测试")
        Text(
            "点击测试Cs服务", color = Color.Red,
            fontSize = 32.sp,
            modifier = Modifier.clickable {
                "bridge://app/featureAndroid".call(ctx)
            })

        "kt://app/call".CfCall()
        "kt://app/view/feature1".CfCall()

        Column(modifier = Modifier.padding(30.dp)) {
            val count by  vm.count.collectAsState()
            val request  by remember {
                mutableStateOf("kt://app/view/featureMvvm".toCfParams(vm))
            }
            Button({
                vm.increment()
            }) {
                Text("VM点击测试:$count")
            }
            request.call()
        }


        Column(modifier = Modifier.padding(30.dp)) {
            Button({
                "bridge://app/feature2/test1".call("click","info" to "传递参数测试")
            }) {
                Text("测试CService数据传递,看日志输出")
            }

            var count by remember { mutableStateOf(0) }
            Button(modifier = Modifier.padding(top = 10.dp), onClick = {
                 "bridge://app/feature2/test2".get<Int>("count" to count)?.let {
                     count = it
                 }
            }) {
                Text("点击，测试服务处理数据：$count")
            }
        }
        Button({
            navHostController.navigate("screen://app/feature1")
        }) {
            Text("点击去另一个页面")
        }

    }
}


