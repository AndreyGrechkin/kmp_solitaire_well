plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.core.common)
            implementation(projects.core.utils)
        }
    }
}

android {
    namespace = "com.defey.solitairewell.features.well.logic"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}