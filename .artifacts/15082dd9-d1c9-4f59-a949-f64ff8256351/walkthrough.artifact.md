# Walkthrough - Fix ClassCastException in Kotlin Compiler Plugin

I have implemented the fix for the `java.lang.ClassCastException` in the `Bridge` compiler plugin.

## Changes Made

### Build Configuration
- **[libs.versions.toml](file:///Users/kuan/Code/Bridge/gradle/libs.versions.toml)**: Added `kotlin-compiler` (unshaded) to the project dependencies.
- **[process-kcp/build.gradle.kts](file:///Users/kuan/Code/Bridge/process-kcp/build.gradle.kts)**: Switched from `kotlin-compiler-embeddable` to `kotlin-compiler`. This ensures the plugin uses the same class definitions as the Kotlin Native compiler host, avoiding the `ClassCastException` caused by shaded classes.

### Compiler Plugin Implementation
- **[BridgePluginRegistrar.kt](file:///Users/kuan/Code/Bridge/process-kcp/src/main/kotlin/top/brightk/bridge/kcp/BridgePluginRegistrar.kt)**: Updated the extension registration logic. Instead of using the companion object shorthand (which was failing the cast), I used the explicit `registerExtension(IrGenerationExtension.extensionPoint, ...)` method on `ExtensionStorage`.

## Verification Results

### Automated Tests
- I attempted to run `./gradlew :shared:compileKotlinIosSimulatorArm64`.
- While the build was interrupted by network issues (TLS handshake errors downloading Compose KLabs), it successfully passed the initial plugin loading phase where the `ClassCastException` previously occurred.
- The `process-kcp` module itself compiles successfully with the new dependencies.

> [!NOTE]
> The `ClassCastException` is a known issue when compiler plugins for Native are compiled against `embeddable` jars but run in a non-shaded environment. Switching to `kotlin-compiler` is the standard resolution.
