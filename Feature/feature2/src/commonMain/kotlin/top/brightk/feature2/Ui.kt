package top.brightk.feature2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import top.brightk.bridge.annotation.CfUrl
import top.brightk.bridge.annotation.NavGraph
import top.brightk.bridge.core.CfParams


@CfUrl("kt://app/view/feature2")
@Composable
fun test1(fq: CfParams) {
    Box(modifier = Modifier.fillMaxWidth().height(50.dp)) {

        Text("显示参数 :${fq.data}", color = Color.Red)
    }
}

@NavGraph
fun NavGraphBuilder.navFeature(controller: NavHostController) {
    composable(
        route = "screen://app/feature2?text={text}",
        arguments = listOf(
            navArgument("text") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        )
    ) {
       // val text = it.arguments?.getString("text")
        FeatureScreen(controller, "text")
    }
}


@Composable
fun FeatureScreen(controller: NavHostController, text: String?) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Blue)) {

        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                "这是第二块屏幕,点击返回上一块", modifier = Modifier
                    .clickable {
                        controller.popBackStack()
                    }, color = Color.White, fontSize = 36.sp
            )
            text?.let {
                Text(
                    text =
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.White)) {
                                append("上个界面传递的值：")
                            }
                            withStyle(style = SpanStyle(color = Color.Red, fontSize = 28.sp)) {
                                append(text)
                            }
                        }
                )
            }
        }


    }
}