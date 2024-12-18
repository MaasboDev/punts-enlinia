plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.maasbodev.puntsenlinea.design_system"
    compileSdk = 35
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
}
