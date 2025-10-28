import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

compose.resources {
    generateResClass = never
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.android.compose)
            implementation(libs.koin.android.navigation)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.data.database)
            implementation(projects.resources)
            implementation(projects.core.common)
            implementation(projects.uiKit)
            implementation(projects.core.navigation)
            implementation(projects.features.well.ui)
            implementation(projects.features.settings.ui)
            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.androidx.navigation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(compose.foundation)
            implementation(compose.material3)
        }
    }
}



android {
    namespace = "com.defey.solitairewell"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.defey.solitairewell"
        minSdk = libs.versions.android.minSdk.get().toInt()
        //noinspection OldTargetApi
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        val keystorePropertiesFile = rootProject.file("keystore.properties")
        val keystoreProperties = Properties()
        if (keystorePropertiesFile.exists()) {
           keystoreProperties.load(FileInputStream(keystorePropertiesFile))
        }

        create("release") {
            storeFile = file(keystoreProperties.getProperty("storeFile") ?: "debug.keystore")
            storePassword = keystoreProperties.getProperty("storePassword") ?: ""
            keyAlias = keystoreProperties.getProperty("keyAlias") ?: ""
            keyPassword = keystoreProperties.getProperty("keyPassword") ?: ""
        }
    }
    buildTypes {
        getByName("debug") {
            // Настройки для отладки
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            // Отключаем рекламу в debug
            buildConfigField("boolean", "SHOW_ADS", "false")
            buildConfigField("String", "AD_NETWORK", "\"MOCK\"")

            // Для manifest placeholders
            manifestPlaceholders["adsEnabled"] = "false"
        }

        getByName("release") {
            // Настройки для продакшена
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            // Включаем рекламу в release
            buildConfigField("boolean", "SHOW_ADS", "true")
            buildConfigField("String", "AD_NETWORK", "\"VK\"")

            // Для manifest placeholders
            manifestPlaceholders["adsEnabled"] = "true"

            // Proguard правила
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.defey.solitairewell.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.defey.solitairewell"
            packageVersion = "1.0.0"
        }
    }
}
