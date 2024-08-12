plugins {
    alias(libs.plugins.step.tracker.android.library)
}

android {
    namespace = "com.tareq.core.notification"

}

dependencies {

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designsystem)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
}