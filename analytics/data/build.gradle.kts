plugins {
    alias(libs.plugins.step.tracker.android.library)
}

android {
    namespace = "com.tareq.analytics.data"
}

dependencies {

    // Modules
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)

    implementation(libs.kotlinx.coroutines.core)
}