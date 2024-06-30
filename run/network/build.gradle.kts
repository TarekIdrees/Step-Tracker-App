plugins {
    alias(libs.plugins.step.tracker.android.library)
}

android {
    namespace = "com.tareq.run.network"
}

dependencies {
    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.data)
}