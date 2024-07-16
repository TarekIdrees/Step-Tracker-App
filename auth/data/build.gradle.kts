plugins {
    alias(libs.plugins.step.tracker.android.library)
    alias(libs.plugins.step.tracker.jvm.ktor)
}

android {
    namespace = "com.tareq.auth.data"
}

dependencies {

    // Modules
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    //Koin
    implementation(libs.bundles.koin)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}