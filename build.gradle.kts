plugins {
    alias(libs.plugins.detekt)
}

detekt {
    config.from(rootProject.files("tools/linters/detekt.yml"))
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

dependencies {
    detektPlugins(libs.detekt.plugin)
}