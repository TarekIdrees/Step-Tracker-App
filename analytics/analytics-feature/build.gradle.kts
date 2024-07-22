plugins {
    alias(libs.plugins.step.tracker.android.dynamic.feature)
}
android {
    namespace = "com.tareq.analytics.analytics_feature"
}

dependencies {
    implementation(project(":app"))

    api(projects.analytics.presentation)
    implementation(projects.analytics.domain)
    implementation(projects.analytics.data)
    implementation(projects.core.database)
    implementation(libs.androidx.core.ktx)
}