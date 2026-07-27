# Implementation Plan - Standardize KCP and Fix Version Mismatch

This plan addresses the `ClassCastException` by removing legacy K1 code, standardizing on the K2 API for Kotlin 2.4.10, and fixing a critical version mismatch in the Gradle plugin.

## Proposed Changes

### [BridgePluginRegistrar.kt](file:///Users/kuan/Code/Bridge/process-kcp/src/main/kotlin/top/brightk/bridge/kcp/BridgePluginRegistrar.kt)
- **[MODIFY]**: Remove the `ProjectExtensionDescriptor` anonymous object (legacy K1 hack).
- **[MODIFY]**: Use the standard K2 API: `IrGenerationExtension.registerExtension(BridgeIrGenerationExtension(logger))`.
- **[MODIFY]**: Update the startup log message to reflect the new version.

### [BridgeKotlinGradlePlugin.kt](file:///Users/kuan/Code/Bridge/process-gradle-plugin/src/main/kotlin/top/brightk/bridge/gradle/BridgeKotlinGradlePlugin.kt)
- **[MODIFY]**: Update `getPluginArtifact` version from `0.1.4` to the current development version (`0.2.7`).
- **[IMPORTANT]**: Hardcoded version mismatches (e.g., expecting 0.1.4 but using 0.2.x locally) can cause Gradle to mix multiple versions of the plugin JAR on the compiler classpath, leading to `ClassCastException`.

### Build Configuration
- **[process-gradle-plugin/build.gradle.kts](file:///Users/kuan/Code/Bridge/process-gradle-plugin/build.gradle.kts)**: Keep it clean, rely on project-level version management.
- **[process-kcp/build.gradle.kts](file:///Users/kuan/Code/Bridge/process-kcp/build.gradle.kts)**: Bump version to **0.2.7**.

## Verification Plan

### Manual Verification
1. Run `./gradlew clean` to ensure all old JARs (especially 0.1.4) are purged.
2. Run `./gradlew --stop` to kill the Gradle daemon.
3. Execute the iOS compilation: `./gradlew :shared:compileKotlinIosSimulatorArm64`.
4. Verify that only one version of the registrar starts (log check) and that no `ClassCastException` occurs.
