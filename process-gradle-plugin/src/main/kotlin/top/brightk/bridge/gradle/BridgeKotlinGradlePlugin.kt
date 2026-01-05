package top.brightk.bridge.gradle

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

/**
 * 核心：这个类负责将你的 KCP (bridge-kcp) 注入到 Kotlin 编译流程中
 */
class BridgeKotlinGradlePlugin : KotlinCompilerPluginSupportPlugin {

    // 1. 定义插件 ID，用户在 shared 模块 apply 时使用
    override fun getCompilerPluginId(): String = "bridge-compiler-plugin"

    // 2. 指向你的 KCP 模块。Gradle 会自动下载/关联这个 artifact 的 Jar 包
    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = "top.brightk",
        artifactId = "process-kcp", // 必须对应你那个包含 BridgePluginRegistrar 的模块名
        version = "0.1.0"          // 对应你的插件版本
    )
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
        return true
    }
    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>
    ): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        // 【本地调试核心】强制关联本地 KCP 模块
        project.dependencies.add("kotlinCompilerPluginClasspath", project.project(":process-kcp"))
        return project.provider {
            // 目前没有参数，返回空列表
            emptyList<SubpluginOption>()
        }
    }
}