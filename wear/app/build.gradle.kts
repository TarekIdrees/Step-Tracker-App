plugins {
    alias(libs.plugins.step.tracker.android.application.wear.compose)
}

android {
    namespace = "com.tareq.wear.app"

    defaultConfig {
        minSdk = 30
    }
}

dependencies {

    // Modules
    implementation(projects.core.presentation.designsystemWear)

    implementation(projects.wear.run.presentation)
    implementation(projects.wear.run.domain)
    implementation(projects.wear.run.data)

    implementation(projects.core.connectiivity.domain)
    implementation(projects.core.connectiivity.data)

    implementation(projects.core.notification)


    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.bundles.koin.compose)
}