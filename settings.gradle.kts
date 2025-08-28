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
include(":Feature:feature1")
include(":Feature:feature2")
