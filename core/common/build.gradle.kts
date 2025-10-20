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
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
        }
    }
}

android {
    namespace = "com.defey.solitairewell.core.common"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}