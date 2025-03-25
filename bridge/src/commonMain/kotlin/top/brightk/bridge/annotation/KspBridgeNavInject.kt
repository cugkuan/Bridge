package top.brightk.bridge.annotation

/**
 * ksp 处理的过渡注解，没有实际意义
 */
@Target(AnnotationTarget.FIELD)
annotation class KspBridgeNavInject(val className:String)
