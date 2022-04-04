/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our samples at https://docs.gradle.org/7.3.3/samples
 */

plugins {
    java
    kotlin("jvm") version "1.6.10"
    application
}

group = "at.stnwtr"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/io.javalin/javalin
    implementation("io.javalin:javalin:4.4.0")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:1.7.36")

    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:1.14.3")
}

application.mainClass.set("at.stnwtr.asboet.LauncherKt")
