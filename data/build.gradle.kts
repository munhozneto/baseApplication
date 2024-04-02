plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
}

android {
     defaultConfig {
        minSdk = DefaultConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(BuildType.debug) {
            buildConfigField("String", "SERVER_URL", URLs.Debug.SERVER_URL)
        }
        getByName(BuildType.release) {
            buildConfigField("String", "SERVER_URL", URLs.Release.SERVER_URL)
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileSdk = DefaultConfig.compileSdk
    buildToolsVersion = DefaultConfig.buildToolsVersion

    namespace = "com.pmn.data"

    compileOptions {
        sourceCompatibility = DefaultConfig.javaCompatibilityVersion
        targetCompatibility = DefaultConfig.javaCompatibilityVersion
    }
    kotlinOptions {
        jvmTarget = DefaultConfig.kotlinCompatibilityVersion.toString()
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(project(Modules.domain))

    implementation(libs.androidx.core.ktx)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.bundles.retrofit)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}