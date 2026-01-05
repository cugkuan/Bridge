enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {

    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }

    includeBuild("process-gradle-plugin")
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}



rootProject.name = "BridgeApp"
include(":androidApp")
include(":bridge")
include(":process")
include(":process-kcp")
//include(":process-gradle-plugin")
include(":Feature:feature1")
include(":Feature:feature2")
include(":shared")

