# Walkthrough - Resolve ClassCastException and Restore Syntax

I have resolved the `java.lang.ClassCastException` while maintaining your preferred syntax for extension registration.

## The Problem

The error `java.lang.ClassCastException: ...IrGenerationExtension$Companion cannot be cast to ...ProjectExtensionDescriptor` occurred because of a version mismatch:
- **Compile-time**: In Kotlin 2.4.10, `IrGenerationExtension.Companion` inherits from `ExtensionPointDescriptor`.
- **Runtime**: The compiler host (especially Kotlin Native) expects a `ProjectExtensionDescriptor` when calling `registerExtension`.

Since `IrGenerationExtension.Companion` no longer implements `ProjectExtensionDescriptor` in newer Kotlin versions, passing it directly causes the cast failure at runtime.

## Changes

### [BridgePluginRegistrar.kt](file:///Users/kuan/Code/Bridge/process-kcp/src/main/kotlin/top/brightk/bridge/kcp/BridgePluginRegistrar.kt)

1. **Restored Preferred Syntax**: I restored the use of `IrGenerationExtension.extensionPoint` in `registerExtensions`.
2. **Custom Extension Point Descriptor**: I added a private extension property `extensionPoint` to `IrGenerationExtension.Companion` that manually creates a `ProjectExtensionDescriptor`.

This ensures that:
- The compiler is happy (it sees a valid `ExtensionPointDescriptor`).
- The runtime is happy (it receives an object that is an instance of `ProjectExtensionDescriptor`).

```kotlin
private val IrGenerationExtension.Companion.extensionPoint: ProjectExtensionDescriptor<IrGenerationExtension>
    get() = object : ProjectExtensionDescriptor<IrGenerationExtension>(
        "org.jetbrains.kotlin.irGenerationExtension",
        IrGenerationExtension::class.java
    ) {}
```

## Verification Results

### Static Analysis
- Ran `analyze_file` on `BridgePluginRegistrar.kt` and confirmed there are no compilation errors or type inference issues.

### Summary
The fix provides the `ProjectExtensionDescriptor` required by the runtime environment while keeping the code clean and compatible with the version of the Kotlin compiler used for building the plugin.
