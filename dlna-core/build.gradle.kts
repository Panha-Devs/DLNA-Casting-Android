
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.android.cast.dlna.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        lint.targetSdk = 34
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Cling library required
    api(libs.cling.core)
    api(libs.cling.support)

    // Servlet
    api(libs.javax.servlet.api)

    // Jetty
    api(libs.jetty.server)
    api(libs.jetty.servlet)
    api(libs.jetty.client)

    // Nano HTTP
    api(libs.nanohttpd)

    // Kotlin
    implementation(libs.kotlin.stdlib)
}