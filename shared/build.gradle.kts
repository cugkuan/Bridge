import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.composeMultiplatform)
    id("top.brightk.bridge")
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.navigation.compose)
                implementation(projects.bridge)
                implementation(project(":Feature:feature1"))
                implementation(project(":Feature:feature2"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.kotlin.reflect)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
}

android {
    namespace = "top.brightk.shared"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    val ksp = projects.process
    add("kspCommonMainMetadata", ksp)
    add("kspAndroid", ksp)
    add("kspIosArm64", ksp)
    add("kspIosSimulatorArm64", ksp)

    configurations.matching { it.name.contains("kotlinCompilerPluginClasspath") }.configureEach {
        project.dependencies.add(this.name, project(":process-kcp"))
    }
}

ksp {
    arg("navInject", "true")
    arg("bridgeEntry", "true")
}
