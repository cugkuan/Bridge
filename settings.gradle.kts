enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "bridge"
include(":androidApp")
include(":shared")
include(":process")
include(":feature1")
include(":feature2")
include(":kcpPlugin")
