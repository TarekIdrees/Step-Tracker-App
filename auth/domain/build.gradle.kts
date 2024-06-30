plugins {
    alias(libs.plugins.step.tracker.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}