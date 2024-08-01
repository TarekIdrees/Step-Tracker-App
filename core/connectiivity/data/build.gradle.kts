plugins {
    alias(libs.plugins.step.tracker.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.tareq.core.connectivity.data"
}

dependencies {

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.connectiivity.domain)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.android.gms.play.services.wearable)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.koin)

}