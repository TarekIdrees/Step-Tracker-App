plugins {
    alias(libs.plugins.step.tracker.android.library)
    alias(libs.plugins.step.tracker.android.room)
}

android {
    namespace = "com.tareq.core.database"
}

dependencies {
    // Modules
    implementation(projects.core.domain)

    implementation(libs.org.mongodb.bson)

    // Koin
    implementation(libs.bundles.koin)
}