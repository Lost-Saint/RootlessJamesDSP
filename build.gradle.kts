// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()  // Google's Maven repository
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:9.2.1")
        classpath("com.google.gms:google-services:4.5.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.7")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.0")
    }
}

plugins {
    id("com.android.application") version "9.2.1" apply false
    id("com.android.library") version "9.2.1" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
