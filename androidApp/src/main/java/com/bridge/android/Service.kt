package com.bridge.android

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import top.brightk.bridge.CsService
import top.brightk.bridge.annotation.CfUrl
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.core.CfParams
import top.brightk.bridge.core.Succeed
import top.brightk.bridge.core.CsRequest
import top.brightk.bridge.core.UriRespond


//@CfUrl("kt://app/call")
//@Composable
//fun test(request: CfParams){
//    Text("这是一个服务测试")
//}
//
//
//@CsUrl("kt://app/test")
//class Service :CsService {
//    override fun call(request: CsRequest): UriRespond {
//        Log.e("lmk","服务测试")
//        return Succeed("")
//    }
//}