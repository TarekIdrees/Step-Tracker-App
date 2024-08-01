plugins {
    alias(libs.plugins.step.tracker.jvm.library)
}

dependencies {

    // Modules
    implementation(projects.core.domain)

    implementation(libs.kotlinx.coroutines.core)
}
