#!/bin/bash
rm -rf android .github
mkdir -p .github/workflows
mkdir -p android/app/src/main/java/com/example/whatsapp

cat << 'YML' > .github/workflows/build.yml
name: Build APK
on: [push, workflow_dispatch]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Gradle
        run: |
          cd android
          wget -q https://services.gradle.org/distributions/gradle-8.5-bin.zip
          unzip -q gradle-8.5-bin.zip
          echo "sdk.dir=/usr/local/lib/android/sdk" > local.properties
          ./gradle-8.5/bin/gradle wrapper
          chmod +x gradlew
          ./gradlew assembleDebug
      - uses: actions/upload-artifact@v4
        with:
          name: whatsapp-apk
          path: android/app/build/outputs/apk/debug/app-debug.apk
YML

cat << 'GRADLE' > android/build.gradle
buildscript {
    repositories { google(); mavenCentral() }
    dependencies { classpath 'com.android.tools.build:gradle:8.1.0' }
}
allprojects { repositories { google(); mavenCentral() } }
GRADLE

cat << 'GRADLE' > android/settings.gradle
rootProject.name = "WhatsApp"
include ':app'
GRADLE

cat << 'GRADLE' > android/app/build.gradle
apply plugin: 'com.android.application'
android {
    namespace 'com.example.whatsapp'
    compileSdk 34
    defaultConfig {
        applicationId "com.example.whatsapp"
        minSdk 26
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }
}
dependencies { implementation 'androidx.appcompat:appcompat:1.6.1' }
GRADLE

cat << 'XML' > android/app/src/main/AndroidManifest.xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET" />
    <application android:label="WhatsApp" android:supportsRtl="true">
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
XML

cat << 'JAVA' > android/app/src/main/java/com/example/whatsapp/MainActivity.java
package com.example.whatsapp;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("WhatsApp");
        tv.setTextSize(30);
        tv.setGravity(android.view.Gravity.CENTER);
        tv.setBackgroundColor(0xFF075E54);
        tv.setTextColor(0xFFFFFFFF);
        setContentView(tv);
    }
}
JAVA

echo "Файлы созданы."
