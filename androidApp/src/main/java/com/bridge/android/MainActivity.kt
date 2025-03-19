package com.bridge.android

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.brightk.bridge.Bridge
import top.brightk.bridge.Greeting
import top.brightk.bridge.core.CfParams
import top.brightk.bridge.core.UriRequest
import top.brightk.feature1.TestViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInject()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(Greeting().greet(), this@MainActivity)
                }
            }
        }
    }
}


@Composable
fun GreetingView(text: String, ctx: Context? = null,vm:TestViewModel = TestViewModel() ) {
    Column {
        Text(text = "测试")
        Text(
            "点击测试Cs服务", color = Color.Red,
            fontSize = 32.sp,
            modifier = Modifier.clickable {
                UriRequest("bridge://app/featureAndroid", context = ctx)
                    .call()
            })

        Bridge.call( CfParams("kt://app/call"))
        Bridge.call(CfParams("kt://app/view/feature1"))


        Column(modifier = Modifier.padding(30.dp)) {

            val count by  vm.count.collectAsState()

            var request  by remember {
                mutableStateOf( CfParams("kt://app/view/featureMvvm").apply {
                    data = vm
                })
            }
            Button({
                vm.increment()
            }) {
                Text("VM点击测试:$count")
            }
            Bridge.call(request)
        }

    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
