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

    implementation(libs.timber)
}