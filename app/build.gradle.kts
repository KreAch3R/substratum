/*
 * Copyright (c) 2016-2019 Projekt Substratum
 * This file is part of Substratum.
 *
 * SPDX-License-Identifier: GPL-3.0-Or-Later
 */

import java.io.FileInputStream
import java.io.IOException
import java.util.Properties

plugins {
    id("com.android.application")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")

fun gitHash(): String {
    return try {
        return Runtime.getRuntime().exec("git describe --tags").inputStream.reader().use { it.readText() }.trim()
    } catch (ignored: IOException) {
        ""
    }
}

android {
    compileSdkVersion(28)
    dataBinding.isEnabled = true
    defaultConfig {
        applicationId = "projekt.substratum"
        minSdkVersion(26)
        targetSdkVersion(28)
        versionCode = 1021
        versionName = "one thousand twenty one"
        buildConfigField("java.util.Date", "buildTime", "new java.util.Date(${System.currentTimeMillis()}L)")
        buildConfigField("String", "GIT_HASH", "\"${gitHash()}\"")
        setProperty("archivesBaseName", "substratum_${gitHash()}")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    if (keystorePropertiesFile.exists()) {
        val keystoreProperties = Properties()
        keystoreProperties.load(FileInputStream(keystorePropertiesFile))

        signingConfigs {
            create("release") {
                keyAlias = keystoreProperties["keyAlias"].toString()
                keyPassword = keystoreProperties["keyPassword"].toString()
                storeFile = file(keystoreProperties["storeFile"].toString())
                storePassword = keystoreProperties["storePassword"].toString()
            }
        }
        buildTypes.getByName("release").signingConfig = signingConfigs.getByName("release")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            //proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    lintOptions {
        isAbortOnError = true
        disable("MissingTranslation")
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    val aboutVersion = "7.0.0"
    val androidXVersion = "1.0.0"
    val apkSigVersion = "3.4.0"
    val caocVersion = "2.2.0"
    val commonsIoVersion = "2.6"
    val databindingVersion = "3.3.1"
    val expandableLayoutVersion = "2.9.2"
    val fabSheetVersion = "1.2.1"
    val floatingHeadVersion = "2.4.4"
    val gestureRecyclerVersion = "1.8.0"
    val glideVersion = "4.9.0"
    val imageCropperVersion = "2.8.0"
    val svgViewVersion = "1.0.6"
    val welcomeVersion = "1.4.1"
    val ztZipVersion = "1.13"

    annotationProcessor("com.github.bumptech.glide:compiler:$glideVersion")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.1.0-beta01")
    implementation("androidx.cardview:cardview:$androidXVersion")
    implementation("androidx.databinding:databinding-runtime:$databindingVersion")
    implementation("androidx.palette:palette:$androidXVersion")
    implementation("androidx.recyclerview:recyclerview:1.1.0-alpha06")
    implementation("cat.ereza:customactivityoncrash:$caocVersion")
    implementation("commons-io:commons-io:$commonsIoVersion")
    implementation("com.android.tools.build:apksig:$apkSigVersion")
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    implementation("com.github.recruit-lifestyle:FloatingView:$floatingHeadVersion")
    implementation("com.google.android.material:material:1.1.0-alpha07")
    implementation("com.gordonwong:material-sheet-fab:$fabSheetVersion")
    implementation("com.jaredrummler:animated-svg-view:$svgViewVersion")
    implementation("com.mikepenz:aboutlibraries:$aboutVersion@aar") {
        isTransitive = true
    }
    implementation("com.stephentuso:welcome:$welcomeVersion")
    implementation("com.theartofdev.edmodo:android-image-cropper:$imageCropperVersion")
    implementation("com.thesurix.gesturerecycler:gesture-recycler:$gestureRecyclerVersion")
    implementation("net.cachapa.expandablelayout:expandablelayout:$expandableLayoutVersion")
    implementation("org.zeroturnaround:zt-zip:$ztZipVersion")
}
