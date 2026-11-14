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
      - name: Setup Android SDK
        uses: android-actions/setup-android@v3
      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v3
        with:
          gradle-version: '8.5'
      - name: Build with Gradle
        run: |
          cd android
          echo "sdk.dir=/usr/local/lib/android/sdk" > local.properties
          gradle wrapper --gradle-version 8.5
          chmod +x gradlew
          ./gradlew assembleDebug
      - uses: actions/upload-artifact@v4
        with:
          name: whatsapp-apk
          path: android/app/build/outputs/apk/debug/app-debug.apk
