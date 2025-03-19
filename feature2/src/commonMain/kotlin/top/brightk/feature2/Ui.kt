package top.brightk.feature2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.brightk.bridge.annotation.CfUrl
import top.brightk.bridge.core.CfParams


@CfUrl("kt://app/view/feature2")
@Composable
fun test1(fq:CfParams) {
    Box(modifier = Modifier.fillMaxWidth().height(50.dp)) {

        Text("显示参数 :${fq.data}", color = Color.Red)
    }

}