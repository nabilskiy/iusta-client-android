import dependencies.PresentationDep

plugins {
    id(Config.Plugins.androidLibrary)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.kotlinKapt)
}

android {
    compileSdk = Config.Android.androidCompileSdkVersion

    defaultConfig {
        minSdk = Config.Android.androidMinSdkVersion
        targetSdk = Config.Android.androidTargetSdkVersion
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
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
    implementation(project(Modules.domain))

    implementation(PresentationDep.kotlin)
    implementation(PresentationDep.coroutineCore)
    // Dagger-Hilt
    implementation(PresentationDep.daggerHilt)
    kapt(PresentationDep.daggerHiltKapt)
    // JavaX
    implementation(PresentationDep.javax)
    // LifeCycle
    PresentationDep.lifeCycle.forEach {
        implementation(it)
    }

}