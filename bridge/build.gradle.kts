import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.publish)
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
        commonMain {
            dependencies {
                implementation(libs.kotlin.reflect)
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
        androidMain {
            dependencies {}
        }
        iosX64Main {
            dependencies {}
        }
        iosArm64Main {
            dependencies {}
        }
        iosSimulatorArm64Main {
            dependencies {}
        }
    }
}
android {
    namespace = "top.brightk.bridge"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
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
version = "0.0.7.0"

mavenPublishing {
    coordinates("top.brightk", "bridge", project.version.toString())
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    pom {
        name.set("Bridge")
        description.set(
            "Bridge is a lightweight componentized framework work on KMP \n" +
                    "Bridge 是一个轻量级的组件化框架，可以在KMP上工作。\n" +
                    "\n"
        )
        inceptionYear = "2025"
        url = "https://github.com/cugkuan/Bridge/"
        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                description = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id.set("BrightK")
                name.set("BrightK")
                email.set("cugkuan@163.com")
                url = "https://github.com/cugkuan/Bridge"
            }
        }
        scm {
            url = "https://github.com/cugkuan/Bridge/"
            connection = "https://github.com/cugkuan/Bridge.git"
            developerConnection = "https://github.com/cugkuan/Bridge.git"
        }
    }
}



