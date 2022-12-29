package dependencies

object UiDep {

    // Kotlin
    const val kotlin = Dependencies.KotlinDep.kotlin

    // Core
    const val coreKtx = Dependencies.CoreDep.coreKtx
    const val appCompat = Dependencies.CoreDep.appCompat
    const val material = Dependencies.CoreDep.material
    const val constraint = Dependencies.CoreDep.constraint
    const val navigationFragmentKtx = Dependencies.CoreDep.navigationFragmentKtx
    const val navigationUiKtx = Dependencies.CoreDep.navigationUiKtx
    const val activityKtx = Dependencies.CoreDep.activityKtx
    const val swipeRefreshLayout = Dependencies.CoreDep.swipeRefreshLayout

    // LifeCycle
    val LifeCycle = listOf(
        Dependencies.LifeCycleDep.viewModelKtx,
        Dependencies.LifeCycleDep.lifeCycleExtension,
        Dependencies.LifeCycleDep.lifeCycleRuntime,
        Dependencies.LifeCycleDep.lifeCycleRuntimeKtx
    )

    // Hilt
    const val daggerHilt = Dependencies.DaggerHiltDep.hiltAndroid
    const val daggerHiltKapt = Dependencies.DaggerHiltDep.hiltAndroidKapt

    // Coroutines
    val Coroutines = listOf(
        Dependencies.CoroutinesDep.coroutineCore,
        Dependencies.CoroutinesDep.coroutineAndroid
    )

    const val imagePicker = Dependencies.ImagePickerDep.imagePicker
    const val glide = Dependencies.GlideDep.glide
    const val glideKapt = Dependencies.GlideDep.glideKapt
    const val timber = Dependencies.TimberDep.timber
    const val lottie = Dependencies.LottieDep.lottie

    //QR scanner
    const val zxing = Dependencies.QrDep.zxing
    const val desugar = Dependencies.QrDep.desugar

    //app locale
    const val locale = Dependencies.LocaleDep.locale

    //FCM
    const val firebaseBom = Dependencies.FirebaseDep.firebaseBom
    val Firebase = listOf(
        Dependencies.FirebaseDep.fcm,
        Dependencies.FirebaseDep.analytics
    )

}