import dependencies.CacheDep

plugins {
    id(Config.Plugins.androidLibrary)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.kotlinKapt)
}

android {
    compileSdk = Config.Android.androidCompileSdkVersion
    buildToolsVersion = Config.Android.androidBuildToolsVersion

    defaultConfig {
        minSdk = Config.Android.androidMinSdkVersion
        targetSdk = Config.Android.androidTargetSdkVersion
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Modules
    implementation(project(Modules.data))
    // Kotlin
    implementation(CacheDep.kotlin)
    // JavaX
    implementation(CacheDep.javax)
    // Room
    CacheDep.room.forEach {
        api(it)
    }
    kapt(CacheDep.roomKapt)

}