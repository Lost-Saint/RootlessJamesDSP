// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()  // Google's Maven repository
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.13.2")
        classpath("com.google.gms:google-services:4.4.3")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.7")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.21")
    }
}

plugins {
    id("com.android.application") version "8.13.2" apply false
    id("com.android.library") version "8.13.2" apply false
    id("org.jetbrains.kotlin.android") version "2.1.21" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
