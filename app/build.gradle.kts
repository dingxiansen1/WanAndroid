import com.dd.version.*

plugins {
    //通过自定义插件进行版本控制
    id("com.dd.app")
    id("com.dd.common")
    id("com.dd.compose")
    id("com.dd.test")
    id("com.dd.room")
    id("com.dd.hilt")
    id("com.dd.serialization")
}

android {
    namespace = "com.android.dd.wanandroidcompose"

    defaultConfig {
        applicationId = "com.android.dd.wanandroidcompose"

        versionCode = Android.versionCode
        versionName = Android.versionName

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Version.compose_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(com.dd.version.Library.netLibrary)

    implementation(project(":common:basicCompose"))
    implementation(project(":common:common"))
    implementation(project(":common:utils"))
    implementation(project(":common:net"))
}
