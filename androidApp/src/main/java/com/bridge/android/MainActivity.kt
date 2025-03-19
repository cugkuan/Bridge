package com.bridge.android

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import top.brightk.bridge.Greeting
import top.brightk.bridge.annotation.Init
import top.brightk.bridge.annotation.Type.DEFAULT
import top.brightk.bridge.core.FcRequest
import top.brightk.bridge.core.UriRequest
import top.brightk.bridge.registerFunction
import top.brightk.bridge.registerService

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
fun GreetingView(text: String, ctx: Context? = null) {
    Column {
        Text(text = "测试")
        Text(
            "点击测试Cs服务", color = Color.Red,
            fontSize = 32.sp,
            modifier = Modifier.clickable {
                UriRequest("bridge://app/featureAndroid", context = ctx)
                    .call()
            })

        FcRequest("kt://app/call")
            .call()
        FcRequest("kt://app/view/feature1")
            .call()

    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
