import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.publish)
}
java {
    withSourcesJar()
    // withJavadocJar()
}

dependencies {
    implementation(kotlin("stdlib")) // Kotlin 标准库
    compileOnly(libs.kotlin.compiler.embeddable) // Kotlin compiler
    //  compileOnly(libs.kcp.native)

}
// 配置 Kotlin 编译目标
kotlin {
    jvmToolchain(11) // 使用 JDK 11
}
group = "top.brightk"
version = "0.1.0"

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates(
        "top.brightk", "bridge-kcp",
        project.version.toString()
    )
    pom {
        // public maven must
        name.set("Bridge kcp")
        description.set("Bridge ksp,work for Bridge;Kcp插件,负责将ksp部分生成的代码插入指定的方法")
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