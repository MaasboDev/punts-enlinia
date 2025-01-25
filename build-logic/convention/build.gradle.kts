import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.maasbodev.puntsenlinia.buildlogic.convention"

java {
	sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
	targetCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
}

tasks.withType<KotlinCompile>().configureEach{
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(libs.versions.javaVersion.get()))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("android-application") {
            id = "com.maasbodev.puntsenlinia.buildlogic.convention.android-application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("android-library") {
            id = "com.maasbodev.puntsenlinia.buildlogic.convention.android-library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("jvm-library") {
            id = "com.maasbodev.puntsenlinia.buildlogic.convention.jvm-library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
