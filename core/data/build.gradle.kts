plugins {
    alias(libs.plugins.step.tracker.android.library)
    alias(libs.plugins.step.tracker.jvm.ktor)
}

android {
    namespace = "com.tareq.core.data"
}

dependencies {
    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.database)

    // Koin
    implementation(libs.bundles.koin)

    // Timber
    implementation(libs.timber)
}