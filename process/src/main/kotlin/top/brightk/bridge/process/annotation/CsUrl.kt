package top.brightk.bridge.process.annotation



@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CsUrl(val uri: String, val type: Type = Type.DEFAULT)