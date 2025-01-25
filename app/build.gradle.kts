import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kover)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.detekt)
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

    compileSdk = 35

    defaultConfig {
        applicationId = "com.maasbodev.puntsenlinia"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(projects.core.designSystem)
    implementation(projects.core.stringResources)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.bundles.compose)
    implementation(libs.splashscreen)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.remote.config)
    implementation(libs.firebase.crashlytics)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.core)

    implementation(libs.junit.jupiter.api)

    kover(projects.core.designSystem)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.bundles.compose.debug)
}

kover {
    reports {
        filters {
            excludes {
                androidGeneratedClasses()
                classes(
                    "dagger.hilt.internal.aggregatedroot.codegen.*",
                    "*_HiltModules",
                    "*_Factory",
                    "*_Impl",
                    "Hilt_*",
                    "*GeneratedInjector",
                )
            }
        }
    }
}

tasks.named("preBuild")
    .configure {
        dependsOn(":copyGitHooks")
    }
