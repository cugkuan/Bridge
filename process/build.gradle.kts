import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.publish)
}
java {
    withSourcesJar()
   // withJavadocJar()
}

dependencies {
    implementation(kotlin("stdlib")) // Kotlin 标准库
    implementation(libs.symbol.processing.api) // KSP API
    implementation(libs.gson)
    compileOnly(libs.kotlin.compiler.embeddable) // Kotlin compiler
}
// 配置 Kotlin 编译目标
kotlin {
    jvmToolchain(11) // 使用 JDK 11
}
group = "top.brightk"
version = "0.0.9.2"

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates("top.brightk", "bridge-ksp",
        project.version.toString())

    pom {
        // public maven must
        name.set("Bridge ksp")
        description.set("Bridge ksp,work for Bridge;Ksp插件,负责自动化注册")
        val pomUrl = "https://github.com/cugkuan/Bridge"
        val pomScm = "https://github.com/cugkuan/Bridge.git"
        url.set(pomUrl)
        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("BrightK")
                name.set("BrightK")
                email.set("cugkuan@163.com")
            }
        }
        scm {
            connection.set(pomUrl)
            developerConnection.set(pomScm)
            url.set(pomUrl)
        }
    }
}