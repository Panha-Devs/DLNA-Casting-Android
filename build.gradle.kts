import com.android.build.gradle.internal.crash.afterEvaluate

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}

subprojects {
    afterEvaluate { project ->
        if (project.plugins.hasPlugin("com.android.library")) {
            project.extensions.configure<PublishingExtension> {
                publications {
                    create<MavenPublication>("maven") {
                        from(project.components["release"])
                        groupId = project.findProperty("GROUP_ID") as String
                        artifactId = project.name
                        version = project.findProperty("VERSION_NAME") as String
                    }
                }
            }
        }
    }
}