# DLNA-Casting-Android
Casting movie in android using DLNA

# Using this library to your project

# Step: 1: Add this jipack to you setting gradle
```
pluginManagement {
    repositories {
        ....

        maven {
            url = uri("https://jitpack.io")
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ....

        maven {
            url = uri("https://jitpack.io")
        }
    }
}
```
# Step: 2: Add this dependencies to you build gradle in app folder
```
dependencies {
    implementation 'com.github.Panha-Devs:DLNA-Casting-Android:Tag'
}
```
