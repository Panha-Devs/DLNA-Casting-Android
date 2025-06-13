// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}

//subprojects {
//    afterEvaluate {
//        plugins.withId("com.android.library") {
//            configure<PublishingExtension> {
//                publications {
//                    create<MavenPublication>("maven") {
//                        from(components["release"])
//                        groupId = "com.github.yourusername"
//                        artifactId = project.name
//                        version = "1.0.0"
//                    }
//                }
//            }
//        }
//    }
//}