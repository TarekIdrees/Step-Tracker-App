plugins {
    alias(libs.plugins.step.tracker.android.feature.ui)
}

android {
    namespace = "com.tareq.auth.presentation"
}

dependencies {

    // Modules
    implementation(projects.core.domain)
    implementation(projects.auth.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}