import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.configureKotlinAndroid
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.configureLibraryBuildTypes
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.getTargetSdk
import com.maasbodev.puntsenlinia.buildlogic.convention.logic.libs

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("soy.gabimoreno.meditation.buildlogic.convention.jvm-test")
                apply("soy.gabimoreno.meditation.buildlogic.convention.android-test")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureLibraryBuildTypes(this)
                testOptions {
                    targetSdk = getTargetSdk()
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
                lint {
                    targetSdk = getTargetSdk()
                }
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
            }
        }
    }
}
