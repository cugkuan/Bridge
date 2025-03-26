import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
    `signing`
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
        publishLibraryVariants("release")
        publishLibraryVariantsGroupedByFlavor = true
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.reflect)
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
            }
        }
        val iosMain by creating {
            dependsOn(commonMain)
        }
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
    }
}
android {
    namespace = "top.brightk.bridge"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

group = "top.brightk"
version = "0.0.4"

apply(from = "${rootProject.projectDir}/gradle/publish.gradle.kts")
val uploadRepository: Action<RepositoryHandler> by extra

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "bridge"
            groupId = "top.brightk"
            version = project.version.toString()
            from(components["kotlin"])
        }
        withType<MavenPublication>().configureEach {
            artifactId = "bridge"
            groupId = "top.brightk"
            version = project.version.toString()
            pom {
                name.set("Bridge")
                description.set(
                    "Bridge is a lightweight componentized framework work on KMP \n" +
                            "Bridge 是一个轻量级的组件化框架，可以在KMP上工作。\n" +
                            "\n"
                )
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
// 下面的配置不要改
signing {
    //useGpgCmd()
    sign(publishing.publications)
    //sign(publishing.publications["maven"])
}




