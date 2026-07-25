# Implementation Plan - Fix ClassCastException in Kotlin Compiler Plugin

The goal is to resolve the `java.lang.ClassCastException` occurring during Kotlin Native compilation when registering the `BridgeIrGenerationExtension`. The error `IrGenerationExtension$Companion cannot be cast to ProjectExtensionDescriptor` typically indicates a mismatch between the plugin's compiled-against Kotlin classes and the compiler's runtime classes, especially in Native environments where `kotlin-compiler-embeddable` shading may conflict with the host compiler.

## User Review Required

> [!IMPORTANT]
> This fix involves changing the compiler dependency from `kotlin-compiler-embeddable` to `kotlin-compiler` and making the extension registration more explicit.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///Users/kuan/Code/Bridge/gradle/libs.versions.toml)
- Add `kotlin-compiler` to the `libraries` section.

#### [MODIFY] [process-kcp/build.gradle.kts](file:///Users/kuan/Code/Bridge/process-kcp/build.gradle.kts)
- Change `compileOnly(libs.kotlin.compiler.embeddable)` to `compileOnly(libs.kotlin.compiler)`.

### Compiler Plugin Implementation

#### [MODIFY] [BridgePluginRegistrar.kt](file:///Users/kuan/Code/Bridge/process-kcp/src/main/kotlin/top/brightk/bridge/kcp/BridgePluginRegistrar.kt)
- Update `registerExtensions` to use a more explicit registration call: `IrGenerationExtension.registerExtension(this, BridgeIrGenerationExtension(...))`.

## Verification Plan

### Manual Verification
- Run the Kotlin Native compilation task (e.g., `./gradlew :shared:compileKotlinIosSimulatorArm64`).
- Verify that the `ClassCastException` no longer occurs and the build proceeds.
