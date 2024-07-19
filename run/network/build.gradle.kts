plugins {
    alias(libs.plugins.step.tracker.android.library)
    alias(libs.plugins.step.tracker.jvm.ktor)
}

android {
    namespace = "com.tareq.run.network"
}

dependencies {
    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.data)

    // Koin
    implementation(libs.bundles.koin)
}