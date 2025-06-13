pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("http://4thline.org/m2")
            isAllowInsecureProtocol = true
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("http://4thline.org/m2")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "DLNA-Casting-Android"
include(":app")
include(":dlna-core")
include(":dlna-dmc")
include(":dlna-dms")
include(":dlna-dmr")
 