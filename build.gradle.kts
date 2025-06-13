import com.android.build.gradle.internal.crash.afterEvaluate

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}

subprojects {
    plugins.withId("com.android.library") {
        extensions.configure<com.android.build.gradle.LibraryExtension>("android") {
            packaging {
                resources {
                    excludes += "META-INF/beans.xml"
                }
            }
        }
    }
}

subprojects {
    plugins.withId("com.android.library") {
        plugins.withId("maven-publish") {
            afterEvaluate {
                extensions.configure<PublishingExtension> {
                    publications {
                        create<MavenPublication>("release") {
                            println("Project name: ${project.name}")
                            from(components["release"])
                            groupId = findProperty("GROUP_ID") as String
                            artifactId = project.name
                            version = findProperty("VERSION_NAME") as String
                        }
                    }
                }
            }
        }
    }
}
