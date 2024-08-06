plugins {
    alias(libs.plugins.step.tracker.android.library.comose)
}

android {
    namespace = "com.tareq.wear.run.presentation"

    defaultConfig {
        minSdk = 30
    }
}

dependencies {

    // Modules
    implementation(projects.core.presentation.designsystemWear)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.connectiivity.domain)
    implementation(projects.core.domain)
    implementation(projects.wear.run.domain)


    // Wear
    implementation(libs.androidx.wear.compose.foundation)
    implementation(libs.androidx.wear.compose.material)
    implementation(libs.androidx.wear.compose.ui.tooling)
    implementation(libs.android.gms.play.services.wearable)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin.compose)
}