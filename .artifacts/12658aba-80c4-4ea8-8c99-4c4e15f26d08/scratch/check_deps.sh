#!/bin/bash
./gradlew :process-kcp:dependencies --configuration compileClasspath -x :androidApp:help > /Users/kuan/Code/Bridge/.artifacts/12658aba-80c4-4ea8-8c99-4c4e15f26d08/scratch/kcp_deps.txt 2>&1
./gradlew :process-gradle-plugin:dependencies --configuration compileClasspath -x :androidApp:help > /Users/kuan/Code/Bridge/.artifacts/12658aba-80c4-4ea8-8c99-4c4e15f26d08/scratch/gradle_plugin_deps.txt 2>&1
