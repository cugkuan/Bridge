plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    `maven-publish`
    `signing`
    id("java")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    compileOnly(libs.kotlin.compiler.embeddable) // Kotlin compiler
}

try {
    apply(from = "${rootProject.rootDir.parent}/gradle/publish.gradle.kts")
}catch (e:Exception){
    apply(from = "${rootDir}/gradle/publish.gradle.kts")
}


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
            artifactId = "bridge-kcp"
            groupId = project.group.toString()
            version = project.version.toString()
            from(components["kotlin"])

            artifact(tasks["sourcesJar"]) // Add sources JAR
            artifact(tasks["javadocJar"]) // Add Javadoc JAR


            pom {
                // public maven must
                name.set("Bridge kcp")
                description.set("Bridge kcp,work for Bridge;Kcp插件,负责自动化注入")
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

