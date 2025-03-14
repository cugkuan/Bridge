plugins {
   // kotlin("jvm")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    `maven-publish` // 用于发布到 Maven 仓库（可选）
}

group = "com.example.processor" // 你的处理器模块的 Group ID
version = "1.0.0" // 版本号

repositories {
    mavenCentral() // Maven 中央仓库
    google() // Google Maven 仓库（KSP 依赖需要）
}

dependencies {
    implementation(kotlin("stdlib")) // Kotlin 标准库
    implementation(libs.symbol.processing.api) // KSP API
    implementation(libs.gson)
}

// 配置 KSP 处理器
ksp {
    arg("option1", "value1") // 可选：传递参数给 KSP 处理器
    arg("option2", "value2")
}

// 发布到 Maven 本地仓库（可选）
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "com.example.processor"
            artifactId = "processor"
            version = "1.0.0"
        }
    }
    repositories {
        mavenLocal() // 发布到本地 Maven 仓库
    }
}

// 配置 Java 兼容性
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

// 配置 Kotlin 编译目标
kotlin {
    jvmToolchain(11) // 使用 JDK 11
}