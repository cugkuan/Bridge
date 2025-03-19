package top.brightk.feature1

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import top.brightk.bridge.annotation.CfUrl
import top.brightk.bridge.core.CfParams


@CfUrl("kt://app/view/featureMvvm")
@Composable
fun MvvmDemoUI(params: CfParams){
    val vm = params.data as TestViewModel
    Test(vm)
}

@Composable
fun Test(viewModel: TestViewModel){
    val count by  viewModel.count.collectAsState()
    Text(text = "这是ViewModel:${count}")
}