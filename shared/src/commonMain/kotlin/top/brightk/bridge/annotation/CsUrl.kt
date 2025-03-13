package top.brightk.bridge.annotation

import top.brightk.bridge.core.Type


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CsUrl(val uri: String, val type:Type = Type.DEFAULT)