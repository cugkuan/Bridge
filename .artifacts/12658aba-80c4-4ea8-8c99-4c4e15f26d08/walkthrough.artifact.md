# Walkthrough - Resolve ClassCastException via Dependency Alignment

I have analyzed the `ClassCastException` and found that it was caused by a combination of K1-style registration and a version leak from Gradle's internal Kotlin libraries.

## The Problem
1. **Mixed Kotlin Versions**: The `:process-gradle-plugin` module was pulling in **Kotlin 2.0.21** via `gradleApi()`, while the rest of the project was using **Kotlin 2.4.10**.
2. **Binary Incompatibility**: In Kotlin 2.4.x, the internal compiler APIs (like `IrGenerationExtension.Companion`) changed their inheritance structure, making them incompatible with code that expects the 2.0.x structure (the `ClassCastException`).
3. **Improper Fix**: The previous "anonymous object" was a K1-era workaround that is not suitable for a K2 `CompilerPluginRegistrar`.

## Changes

### 1. [process-gradle-plugin/build.gradle.kts](file:///Users/kuan/Code/Bridge/process-gradle-plugin/build.gradle.kts)
Added dependency constraints to force `kotlin-stdlib` and `kotlin-reflect` to version **2.4.10**, preventing `gradleApi()` from leaking older versions into the plugin's classpath.

### 2. [BridgePluginRegistrar.kt](file:///Users/kuan/Code/Bridge/process-kcp/src/main/kotlin/top/brightk/bridge/kcp/BridgePluginRegistrar.kt)
- **Standardized K2 API**: Switched to the modern `IrGenerationExtension.registerExtension(BridgeIrGenerationExtension(logger))` registration method.
- **Removed Hack**: Deleted the `ProjectExtensionDescriptor` anonymous object.
- **Log Message**: Updated startup log to version **0.2.5**.

### 3. [process-kcp/build.gradle.kts](file:///Users/kuan/Code/Bridge/process-kcp/build.gradle.kts)
Bumped version to **0.2.5**.

## Verification Results
- **Static Analysis**: `analyze_file` confirms zero errors in the updated code.
- **Alignment**: The classpath of the plugin is now better aligned with the 2.4.10 compiler used to build it.

## Critical Action Required

> [!IMPORTANT]
> To ensure the changes take effect and clear the old broken JARs from your environment, please run:
> 1. `./gradlew clean`
> 2. `./gradlew --stop`
> 3. Perform your test build (e.g., iOS compilation).

If you see `BridgeKcp Registrar version 0.2.5 starting...` in the logs, you are running the correct aligned version.
