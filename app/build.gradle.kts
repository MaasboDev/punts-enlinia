import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.maasbodev.puntsenlinia"

    signingConfigs {
        create("release") {
            val keystoreFile = project.rootProject.file("local.properties")
            val properties = Properties()
            properties.load(keystoreFile.inputStream())
            keyAlias = properties.getProperty("KEYSTORE_KEY_ALIAS")
            storeFile = file(properties.getProperty("KEYSTORE_STORE_FILE"))
            storePassword = properties.getProperty("KEYSTORE_STORE_PASSWORD")
            keyPassword = properties.getProperty("KEYSTORE_KEY_PASSWORD")
        }
    }

    compileSdk = 34

    defaultConfig {
        applicationId = "com.maasbodev.puntsenlinia"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1.0" // MAJOR.MINOR.PATCH.BUILD

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = false // TODO: Change to true
            isShrinkResources = false // TODO: Change to true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.remote.config)
    implementation(libs.firebase.crashlytics)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
