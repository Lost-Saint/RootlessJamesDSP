plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.0")

    implementation(gradleApi())
}

repositories {
    mavenCentral()
    google()
}
