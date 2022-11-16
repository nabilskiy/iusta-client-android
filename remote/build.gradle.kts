import dependencies.RemoteDep

plugins {
    id(Config.Plugins.kotlin)
    id(Config.Plugins.javaLibrary)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Modules
    implementation(project(Modules.data))
    // Kotlin
    implementation(RemoteDep.kotlin)
    // JavaX
    implementation(RemoteDep.javax)
    // Network (Retrofit, OkHttp, Interceptor, Moshi)
    RemoteDep.retrofit.forEach { implementation(it) }
    // Coroutines
    implementation(RemoteDep.coroutineCore)

}