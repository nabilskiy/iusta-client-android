buildscript {
    repositories {
        google()
        mavenCentral()

    }

    dependencies {
        classpath(Config.ClassPaths.androidGradle)
        classpath(Config.ClassPaths.googleGradle)
        classpath(Config.ClassPaths.kotlinGradle)
        classpath(Config.ClassPaths.daggerHiltGradle)
        classpath(Config.ClassPaths.navigationSafArgsGradle)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.android.tools.build:gradle:7.2.2")
    }
}


allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
