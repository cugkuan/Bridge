
# 概述

Bridge 是一个 kmp(kotlin multiplatform) 轻量级组件库。支持跨组件 @Compose 方法调用和基础的组件通信。已经验证在iOS环境中能正常工作。

# 基本使用


在项目级别的 build.gradle.kts 中配置插件

```kt

plugins {
    id("top.brightk.bridge") version '0.1.0' appply false 
}

```
在入口模块中使用插件

```kt

plugins {
    id("top.brightk.bridge")
}

// 标明这个是主模块
ksp {
    arg("bridgeEntry","true")
}

```

具体的可以参考demo中依赖配置

## @Compose 跨组件方法调用

Feature 组件的 @Compose 方法
```koltin
@CfUrl("kt://app/view/feature")
@Composable
fun test1() {
    Box(modifier = Modifier.fillMaxWidth().height(50.dp)) {
        Text("This is Feature组件")
    }
}
```
其它模块使用该方法:

```koltin

@Compose
fun Screen(){
 kt://app/view/feature".CfCall()
}

```
其它更高阶的用法，参考demo

## 组件通信

- 定义组件


```kotlin
@CsUrl("bridge://app/feature2/test1")
class Feature2Service :CsService {
    override fun call(request: CsRequest): UriRespond {
        val value = request.getStringParam("info")
        println( "action:${request.action} : $value :$value")
        return Succeed()
    }
}

```

使用：

```koltin

"bridge://app/feature2/test1".call("click","info" to "传递参数测试")

```

其它高阶用法参考demo



