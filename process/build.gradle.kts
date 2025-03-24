plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    `maven-publish`
    `signing`
    id("java")
}
dependencies {
    implementation(kotlin("stdlib")) // Kotlin 标准库
    implementation(libs.symbol.processing.api) // KSP API
    implementation(libs.gson)
    compileOnly(libs.kotlin.compiler.embeddable) // Kotlin compiler
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

try {
    apply(from = "${rootProject.rootDir.parent}/gradle/publish.gradle.kts")
}catch (e:Exception){
    apply(from = "${rootDir}/gradle/publish.gradle.kts")
}

//java {
//    withJavadocJar()
//    withSourcesJar()
//}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.getByName("javadoc"))
}



group = "top.brightk"
version = "0.0.1"


val uploadRepository: Action<RepositoryHandler> by extra
publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "bridge-ksp"
            groupId = project.group.toString()
            version = project.version.toString()
            from(components["kotlin"])

            artifact(tasks["sourcesJar"]) // Add sources JAR
            artifact(tasks["javadocJar"]) // Add Javadoc JAR

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
    }
    repositories(uploadRepository)
}

signing {
    sign(publishing.publications["release"])
}
