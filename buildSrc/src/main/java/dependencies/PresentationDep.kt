package dependencies

object PresentationDep {
    const val kotlin = Dependencies.KotlinDep.kotlin
    const val javax = Dependencies.JavaDep.javax
    const val coroutineCore = Dependencies.CoroutinesDep.coroutineCore

    // Dagger-Hilt
    const val daggerHilt = Dependencies.DaggerHiltDep.hiltAndroid
    const val daggerHiltKapt = Dependencies.DaggerHiltDep.hiltAndroidKapt

    val lifeCycle = listOf(
        Dependencies.LifeCycleDep.viewModelKtx,
        Dependencies.LifeCycleDep.liveDataKtx,
        Dependencies.LifeCycleDep.lifeCycleExtension,
        Dependencies.LifeCycleDep.lifeCycleRuntime,
        Dependencies.LifeCycleDep.lifeCycleRuntimeKtx
    )

}