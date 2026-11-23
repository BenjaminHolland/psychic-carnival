pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://redirector.kotlinlang.org/maven/kxrpc-grpc")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://redirector.kotlinlang.org/maven/kxrpc-grpc")
    }
    versionCatalogs {
        create("ktorLibs").from("io.ktor:ktor-version-catalog:3.6.1")
    }
}

rootProject.name = "events"

include(":client")
include(":core")
include(":server")
