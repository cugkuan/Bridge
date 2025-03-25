package top.brightk.feature2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import top.brightk.bridge.annotation.CfUrl
import top.brightk.bridge.annotation.NavUrl
import top.brightk.bridge.core.CfParams


@CfUrl("kt://app/view/feature2")
@Composable
fun test1(fq:CfParams) {
    Box(modifier = Modifier.fillMaxWidth().height(50.dp)) {

        Text("显示参数 :${fq.data}", color = Color.Red)
    }

}


@NavUrl("screen://app/feature2")
@Composable
fun FeatureScreen(controller: NavHostController){
    Box(modifier = Modifier.fillMaxSize().background(Color.Blue)){
        Text("这是第二块屏幕,点击返回上一块", modifier = Modifier.align(Alignment.Center)
            .clickable {
                controller.popBackStack()
            }
            , color = Color.White, fontSize = 36.sp)
    }
}