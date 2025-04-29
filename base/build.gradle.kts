plugins {
    id("kotlin-android")
    id("com.android.library")
    id("maven-publish")

    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.daniel.budgetplanner.base"

    defaultConfig {
        minSdk = 26
        compileSdk = 35

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    /* Testing */
    testImplementation(libs.bundles.testing)

    /* Kotlin */

    /* AndroidX */
    implementation(libs.bundles.androidx)

    /* Compose */
    implementation(libs.bundles.compose)

    /* Navigation */
    implementation(libs.bundles.navigation)

    /* Koin */
    implementation(libs.bundles.koin)
}