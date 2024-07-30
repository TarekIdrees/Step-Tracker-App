plugins {
    alias(libs.plugins.step.tracker.android.library)
}

android {
    namespace = "com.tareq.wear.run.data"

    defaultConfig {
        minSdk = 30
    }
}

dependencies {

    // Modules
    implementation(projects.wear.run.domain)
    implementation(projects.core.domain)

    implementation(libs.androidx.health.services.client)
    implementation(libs.bundles.koin)
}
