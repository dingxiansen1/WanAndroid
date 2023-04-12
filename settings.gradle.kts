pluginManagement {
    includeBuild("../CommonDemo/versionPlugin")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "WanAndroidCompose"
include(":app")

include(":common:basicCompose")
project(":common:basicCompose").projectDir = File("../CommonDemo/BasicCompose")
include(":common:common")
project(":common:common").projectDir = File("../CommonDemo/Common")
include(":common:utils")
project(":common:utils").projectDir = File("../CommonDemo/Utils")
include(":common:net")
project(":common:net").projectDir = File("../CommonDemo/Net")

