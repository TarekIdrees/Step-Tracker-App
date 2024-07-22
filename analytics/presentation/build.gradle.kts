plugins {
    alias(libs.plugins.step.tracker.android.feature.ui)
}

android {
    namespace = "com.tareq.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)
}