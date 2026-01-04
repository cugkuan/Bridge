package top.brightk.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeUIViewController
import top.brightk.bridge.CfCall
import top.brightk.bridge.call
import top.brightk.bridge.get
import top.brightk.bridge.toCfParams

actual fun platform() = "iOS"


fun MainViewController() = ComposeUIViewController {

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "测试")
            Text(
                "点击测试Cs服务", color = Color.Red,
                fontSize = 32.sp,
                modifier = Modifier.clickable {
//                    "bridge://app/featureAndroid".call(ctx)
                })

            "kt://app/call".CfCall()
            "kt://app/view/feature1".CfCall()

            Column(modifier = Modifier.padding(30.dp)) {
//                val count by vm.count.collectAsState()
//                val request by remember {
//                    mutableStateOf("kt://app/view/featureMvvm".toCfParams(vm))
//                }
//                Button({
//                    vm.increment()
//                }) {
//                    Text("VM点击测试:$count")
//                }
//                request.call()
            }


            Column(modifier = Modifier.padding(30.dp)) {
                Button({
                    "bridge://app/feature2/test1".call("click", "info" to "传递参数测试")
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
        }
    }
}