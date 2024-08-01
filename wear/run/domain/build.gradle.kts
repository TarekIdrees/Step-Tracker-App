plugins {
    alias(libs.plugins.step.tracker.jvm.library)
    alias(libs.plugins.step.tracker.jvm.ktor)
}

dependencies {

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.connectiivity.domain)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
}