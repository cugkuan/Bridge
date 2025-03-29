import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.ksp)
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "feature1"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(kotlin("reflect"))
            implementation(libs.kotlin.reflect)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.navigation.compose)
            implementation(projects.bridge)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
android {
    namespace = "top.brightk.feature2"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies{
    //implementation(libs.bridge.lib)
    val ksp = projects.process
    add("kspCommonMainMetadata",ksp)
    add("kspAndroid",ksp)
    add("kspIosX64",ksp)
    add("kspIosArm64",ksp)
    add("kspIosSimulatorArm64",ksp)
}

