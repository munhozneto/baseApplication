plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.pmn.domain"
    compileSdk = DefaultConfig.compileSdk
    buildToolsVersion = DefaultConfig.buildToolsVersion

    defaultConfig {
        minSdk = DefaultConfig.minSdk
    }

    buildTypes {
        getByName(BuildType.debug) {}
        getByName(BuildType.release) {}
    }
    compileOptions {
        sourceCompatibility = DefaultConfig.javaCompatibilityVersion
        targetCompatibility = DefaultConfig.javaCompatibilityVersion
    }
    kotlinOptions {
        jvmTarget = DefaultConfig.kotlinCompatibilityVersion.toString()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.bundles.retrofit)

    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}