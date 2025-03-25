plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
}

ksp{
    arg("application","true")
    arg("navInject","true")
}
android {
    namespace = "com.bridge.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.bridge.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
//        getByName("debug"){
//            kotlin{
//                sourceSets.main{
//                    kotlin.srcDir("build/generated/ksp/debug/kotlin")
//                }
//            }
//        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}


//// 配置编译器插件
//
//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//    kotlinOptions.freeCompilerArgs += listOf(
//        "-Xplugin=${project(":kcpPlugin").buildDir}/libs/kcpPlugin.jar" // 应用编译器插件
//    )
//}

dependencies {
   // implementation(libs.bridge.lib) // 版本号要匹配
    implementation(projects.bridge)
    implementation(projects.feature1)
    implementation(projects.feature2)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.symbol.processing.api)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.navigation.compose)

    ksp(projects.process)
    add("kotlinCompilerPluginClasspath", projects.process)
}