# Walkthrough - Standardized K2 KCP and Fixed Version Desync

I have implemented a clean, standardized fix for the `ClassCastException` by aligning all module versions and using the correct Kotlin 2.4.10 registration API.

## Changes

### 1. [BridgeKotlinGradlePlugin.kt](file:///Users/kuan/Code/Bridge/process-gradle-plugin/src/main/kotlin/top/brightk/bridge/gradle/BridgeKotlinGradlePlugin.kt)
- **Fixed Hardcoded Version**: Updated the hardcoded plugin version from `0.1.4` to `0.2.7`.
- **Reason**: This was a major source of the `ClassCastException`. If Gradle was pulling an old `0.1.4` JAR (built with Kotlin 2.0.x) instead of your local development version, the mismatched `IrGenerationExtension` types would crash the compiler at runtime.

### 2. [BridgePluginRegistrar.kt](file:///Users/kuan/Code/Bridge/process-kcp/src/main/kotlin/top/brightk/bridge/kcp/BridgePluginRegistrar.kt)
- **Standardized K2 API**: Removed the legacy `ProjectExtensionDescriptor` anonymous object (K1 hack).
- **Modern Registration**: Used the official `IrGenerationExtension.registerExtension(...)` method.
- **Log Message**: Updated the startup log to reflect version **0.2.7**.

### 3. Build Configuration
- **Version Alignment**: Bumped all plugin modules (`process`, `process-kcp`, `process-gradle-plugin`) to version **0.2.7**.
- **Dependencies**: Kept `kotlin-compiler-embeddable` as the primary compiler dependency.

## Verification Results

### Static Analysis
- `analyze_file` confirms no compilation errors or type inference issues in the new standardized code.

## Critical Final Steps

> [!IMPORTANT]
> To ensure the changes take effect and clear the "ghost" `0.1.4` and other old JARs from your environment, please run:
> 1. `./gradlew clean`
> 2. `./gradlew :process-kcp:publishToMavenLocal` (or your local publication task)
> 3. `./gradlew --stop`
> 4. Run your iOS/Native compilation.

The log message `BridgeKcp Registrar version 0.2.7 starting...` will confirm that the correct, aligned version is finally being loaded.
