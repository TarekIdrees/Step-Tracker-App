plugins {
    alias(libs.plugins.step.tracker.android.library)
    alias(libs.plugins.step.tracker.jvm.ktor)
}

android {
    namespace = "com.tareq.run.data"
}

dependencies {

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.run.domain)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.androidx.work)
    implementation(libs.koin.android.workmanager)
    implementation(libs.kotlinx.serialization.json)

}