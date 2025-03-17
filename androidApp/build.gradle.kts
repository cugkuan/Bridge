plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
}

ksp{
    arg("application","true")
}
android {
    namespace = "top.brightk.bridge.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "top.brightk.bridge.android"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(projects.shared)
    implementation(projects.feature1)
    implementation(projects.feature2)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.symbol.processing.api)

    compileOnly(project(":process"))
    ksp(project(":process"))
    //compileOnly(project(":kcpPlugin"))
    add("kotlinCompilerPluginClasspath", project(":kcpPlugin"))
}