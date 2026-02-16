plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    // iOS targets
    // Only enable on non-Windows (or explicit request) to avoid K/N download issues on Windows environment
    if (!System.getProperty("os.name").startsWith("Windows")) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = "shared"
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.anticosmic.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
