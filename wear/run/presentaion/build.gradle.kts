plugins {
    alias(libs.plugins.step.tracker.android.library.comose)
}

android {
    namespace = "com.tareq.wear.run.presentaion"

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
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