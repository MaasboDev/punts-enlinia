package com.maasbodev.puntsenlinia.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.configureAppBuildTypes
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.configureKotlinAndroid
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.getTargetSdk
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.getVersionCode
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.getVersionName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.maasbodev.puntsenlinia.buildlogic.convention.jvm-test")
                apply("com.maasbodev.puntsenlinia.buildlogic.convention.android-test")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureAppBuildTypes(this)
                defaultConfig {
                    targetSdk = getTargetSdk()
                    versionCode = getVersionCode()
                    versionName = getVersionName()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }
        }
    }
}
