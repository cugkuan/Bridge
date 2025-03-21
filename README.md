
# 概述

Bridge 是一个 kmp(kotlin multiplatform) 轻量级组件库。支持跨组件 @Compose 方法调用和基础的组件通信。

# 基本使用

```kt

// 库依赖
implementation("top.brightk:bridge:0.0.2")

// ksp 依赖
compileOnly("top.brightk:bridge-ksp:0.0.1")
ksp("top.brightk:bridge-ksp:0.0.1")
// kcp 依赖
add("kotlinCompilerPluginClasspath", "top.brightk:bridge-kcp")

```
对于 kmp 模块，依赖如下

```kt

    implementation("top.brightk:bridge:0.0.2")
    val ksp = "top.brightk:bridge-ksp:0.0.2"
    add("kspCommonMainMetadata",ksp)
    add("kspAndroid",ksp)
    add("kspIosX64",ksp)
    add("kspIosArm64",ksp)
    add("kspIosSimulatorArm64",ksp)
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



