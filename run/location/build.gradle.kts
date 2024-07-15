plugins {
    alias(libs.plugins.step.tracker.android.library)
}

android {
    namespace = "com.tareq.run.location"
}

dependencies {

    //Modules
    implementation(projects.core.domain)
    implementation(projects.run.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
}