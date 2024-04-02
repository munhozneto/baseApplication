import org.gradle.api.JavaVersion

object URLs {
    object Debug {
        const val SERVER_URL = "\"https://hml-api.skeelo.com/api/v1/\""
    }

    object Release {
        const val SERVER_URL = "\"https://api.skeelo.com/api/v1/\""
    }
}

object DefaultConfig {
    const val buildToolsVersion = "34.0.0"
    const val appId = "com.pmn.baseapplication"
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val minSdk = 26
    const val targetSdk = 34
    const val compileSdk = 34
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    val javaCompatibilityVersion = JavaVersion.VERSION_17
    val kotlinCompatibilityVersion = JavaVersion.VERSION_17
}


object BuildType {
    const val debug = "debug"
    const val release = "release"
}

object Modules {
    const val app = ":app"
    const val domain = ":domain"
    const val data = ":data"
}