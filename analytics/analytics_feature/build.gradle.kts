plugins {
    alias(libs.plugins.step.tracker.android.dynamic.feature)
}

android {
    namespace = "com.tareq.analytics.analytics_feature"
}

dependencies {
    implementation(project(":app"))
    implementation(libs.androidx.navigation.compose)

    api(projects.analytics.presentation)
    implementation(projects.analytics.domain)
    implementation(projects.analytics.data)
    implementation(projects.core.database)

    // Dynamic feature
    implementation(libs.android.play.feature.delivery)
    implementation(libs.android.play.feature.delivery.ktx)
}