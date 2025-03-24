package top.brightk.bridge.annotation


/**
 * 中间状态，没有实际意义
 */
@Target(AnnotationTarget.FIELD)
annotation class KspBridgeInit(val className:String)