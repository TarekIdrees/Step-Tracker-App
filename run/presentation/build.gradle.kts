plugins {
    alias(libs.plugins.step.tracker.android.feature.ui)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

android {
    namespace = "com.tareq.run.presentation"

}

dependencies {
    //  Modules
    implementation(projects.core.domain)
    implementation(projects.run.domain)
    implementation(projects.core.connectiivity.domain)
    implementation(projects.core.notification)

    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
}