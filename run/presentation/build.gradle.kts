plugins {
    alias(libs.plugins.step.tracker.android.feature.ui)
}

android {
    namespace = "com.tareq.run.presentation"
}

dependencies {
    //  Modules
    implementation(projects.core.domain)
    implementation(projects.run.domain)

    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
}