//import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
  //  alias(libs.plugins.publish)
}

gradlePlugin {
    plugins {
        create("bridgePlugin") {
            id = "top.brightk.bridge"
            implementationClass = "top.brightk.bridge.gradle.BridgeKotlinGradlePlugin"
        }
    }
}

java {
    withSourcesJar()
   // withJavadocJar()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib")) // Kotlin 标准库
    compileOnly(libs.gradle.processing.api)
    compileOnly(libs.gradle.processing)

}
// 配置 Kotlin 编译目标
kotlin {
    jvmToolchain(11) // 使用 JDK 11
}
group = "top.brightk"
version = "0.0.9.8"

//mavenPublishing {
//    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//    signAllPublications()
//    coordinates("top.brightk", "bridge-ksp",
//        project.version.toString())
//
//    pom {
//        // public maven must
//        name.set("Bridge ksp")
//        description.set("Bridge ksp,work for Bridge;Ksp插件,负责自动化注册")
//        val pomUrl = "https://github.com/cugkuan/Bridge"
//        val pomScm = "https://github.com/cugkuan/Bridge.git"
//        url.set(pomUrl)
//        licenses {
//            license {
//                name.set("The Apache Software License, Version 2.0")
//                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//            }
//        }
//        developers {
//            developer {
//                id.set("BrightK")
//                name.set("BrightK")
//                email.set("cugkuan@163.com")
//            }
//        }
//        scm {
//            connection.set(pomUrl)
//            developerConnection.set(pomScm)
//            url.set(pomUrl)
//        }
//    }
//}