import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies{
    implementation(projects.shared)
    add("kspCommonMainMetadata", project(":process"))
    add("kspAndroid", project(":process"))
    add("kspIosX64", project(":process"))
    add("kspIosArm64", project(":process"))
    add("kspIosSimulatorArm64", project(":process"))
}

