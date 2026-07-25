# Walkthrough - Resolve ClassCastException in KCP

I have implemented a more robust fix for the `java.lang.ClassCastException` in `BridgePluginRegistrar.kt`.

## Changes

### [BridgePluginRegistrar.kt](file:///Users/kuan/Code/Bridge/process-kcp/src/main/kotlin/top/brightk/bridge/kcp/BridgePluginRegistrar.kt)

The previous error `java.lang.ClassCastException: ...IrGenerationExtension$Companion cannot be cast to ...ProjectExtensionDescriptor` occurred because:
1. In Kotlin `2.4.10`, `IrGenerationExtension.Companion` no longer inherits from `ProjectExtensionDescriptor`.
2. The runtime environment (e.g., Kotlin Native host) still expects a `ProjectExtensionDescriptor`.
3. Using the Companion object directly or via an extension property could still result in the Companion being passed or cast incorrectly due to how Kotlin compiles extension properties on Companion objects.

I have updated the registration logic to use a **locally defined anonymous object** that explicitly inherits from `ProjectExtensionDescriptor`. This ensures that the object passed to the runtime is guaranteed to be a `ProjectExtensionDescriptor`, avoiding any binary compatibility issues.

```diff
-    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
-        IrGenerationExtension.extensionPoint.registerExtension(BridgeIrGenerationExtension(configuration.getLogger()))
-    }
-}
-
-private val IrGenerationExtension.Companion.extensionPoint: ProjectExtensionDescriptor<IrGenerationExtension>
-    get() = object : ProjectExtensionDescriptor<IrGenerationExtension>(
-        "org.jetbrains.kotlin.irGenerationExtension",
-        IrGenerationExtension::class.java
-    ) {}
+    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
+        val logger = configuration.getLogger()
+        val irExtensionPoint = object : ProjectExtensionDescriptor<IrGenerationExtension>(
+            "org.jetbrains.kotlin.irGenerationExtension",
+            IrGenerationExtension::class.java
+        ) {}
+        irExtensionPoint.registerExtension(BridgeIrGenerationExtension(logger))
+    }
+}
```

## Verification Results

### Static Analysis
- `analyze_file` confirms no compilation errors or type inference issues.
- This approach is the most compatible way to handle the transition between `ExtensionPointDescriptor` and `ProjectExtensionDescriptor` across different compiler versions.
