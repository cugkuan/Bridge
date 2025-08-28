import java.util.Properties


val properties = Properties().apply {
    try {
        load(rootProject.file("./local.properties").inputStream())
    } catch (e: Exception) {
        load(rootProject.file("../local.properties").inputStream())
    }
}
val testUrl: String by properties
val testName: String by properties
val testPassword: String by properties
val ossrhUsername: String by properties
val ossrhPassword: String by properties

val uploadRepository: Action<RepositoryHandler> = Action<RepositoryHandler> {
    maven {
        val publicUrl = when {
            version.toString().endsWith(".test") -> testUrl
            else -> "https://central.sonatype.com/api/v1/publisher/deploy/maven"
        }
        url = uri(publicUrl)
        isAllowInsecureProtocol = true
        credentials {
            if (version.toString().endsWith(".test")) {
                username = testName
                password = testPassword
            } else {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
    mavenLocal()
}
mapOf(
    "uploadRepository" to uploadRepository
).forEach { (name, closure) ->
    project.extra.set(name, closure)
}