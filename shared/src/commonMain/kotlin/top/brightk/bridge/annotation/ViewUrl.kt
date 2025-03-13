package top.brightk.bridge.annotation

/**
 * 这个命名很头疼
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewUrl(val uri: String)