plugins {
    alias(libs.plugins.step.tracker.android.library.comose)
}

android {
    namespace = "com.tareq.core.presentation.designsystem_wear"

    defaultConfig {
        minSdk = 30
    }
}

dependencies {
    api(projects.core.presentation.designsystem)

    implementation(libs.androidx.wear.compose.material)
}