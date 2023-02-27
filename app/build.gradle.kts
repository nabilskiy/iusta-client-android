import dependencies.UiDep

plugins {
    id(Config.Plugins.android)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.googleServices)
    id(Config.Plugins.kotlinKapt)
    id(Config.Plugins.dagger)
    id(Config.Plugins.navigationSafArgs)
}

android {
    compileSdk = Config.Android.androidCompileSdkVersion
    buildToolsVersion = Config.Android.androidBuildToolsVersion

    defaultConfig {
        applicationId = Environments.Release.appId
        minSdk = Config.Android.androidMinSdkVersion
        targetSdk = Config.Android.androidTargetSdkVersion

        versionCode = Environments.Release.appVersionCode
        versionName = Environments.Release.appVersionName

        multiDexEnabled = true
        // Configs
        buildConfigField("String", "BASE_URL", "\"" + Environments.Release.baseUrl + "\"")
        buildConfigField("String", "SECRETKEY", "\"" + Environments.Release.secretKey + "\"")
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.multidex:multidex:2.0.1")
    // Modules
    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.presentation))
    implementation(project(Modules.remote))
    implementation(project(Modules.cache))
    // Core Dependencies
    implementation(UiDep.kotlin)
    implementation(UiDep.coreKtx)
    implementation(UiDep.appCompat)
    implementation(UiDep.material)
    implementation(UiDep.constraint)
    implementation(UiDep.activityKtx)
    implementation(UiDep.swipeRefreshLayout)
        //implementation(UiDep.paging)
    // LifeCycle
    UiDep.LifeCycle.forEach {
        implementation(it)
    }
    // Navigation
    implementation(UiDep.navigationFragmentKtx)
    implementation(UiDep.navigationUiKtx)
    // Dagger-Hilt
    implementation(UiDep.daggerHilt)
    kapt(UiDep.daggerHiltKapt)
    // Glide
    implementation(UiDep.glide)
    kapt(UiDep.glideKapt)
    //Image Picker
    implementation(UiDep.imagePicker)
    //QR scanner
    implementation(UiDep.zxing)
    coreLibraryDesugaring(UiDep.desugar)
    //Locale
    implementation(UiDep.locale)
    //Phone Mask
    implementation(UiDep.phoneMask)
    //Firebase
    implementation(platform(UiDep.firebaseBom))
    UiDep.Firebase.forEach {
        implementation(it)
    }
    // Timber
    implementation(UiDep.timber)
}
