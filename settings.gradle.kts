
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    apply(from = "gradle/dependencies.gradle.kts")
    val settingsRepository : Action<RepositoryHandler> by extra
    repositories(settingsRepository)
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    apply(from = "gradle/dependencies.gradle.kts")
    val settingsRepository : Action<RepositoryHandler> by extra
    repositories(settingsRepository)
}



rootProject.name = "BridgeApp"
include(":androidApp")
include(":bridge")
include(":process")
include(":Feature:feature1")
include(":Feature:feature2")
