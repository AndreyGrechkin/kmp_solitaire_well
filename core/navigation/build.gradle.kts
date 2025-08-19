plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(compose.runtime)
            implementation(compose.animation)
            implementation(compose.foundation)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}

android {
    namespace = "com.defey.solitairewell.core.navigation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
