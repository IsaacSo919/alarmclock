// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    java
    jacoco
    `maven-publish`
    id("org.springframework.boot") version "2.7.8"
}


