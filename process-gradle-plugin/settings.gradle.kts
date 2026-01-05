// process-gradle-plugin/settings.gradle.kts

rootProject.name = "process-gradle-plugin"

// 1. 配置插件下载仓库
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// 2. 配置依赖下载仓库（修复你报错的关键）
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    // 如果你还需要用到 libs.versions.toml，保留这一段
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}