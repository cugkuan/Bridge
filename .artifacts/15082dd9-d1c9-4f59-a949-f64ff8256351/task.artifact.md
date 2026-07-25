# Tasks - Fix ClassCastException in Kotlin Compiler Plugin

- [x] Update build dependencies
    - [x] Add `kotlin-compiler` to `libs.versions.toml`
    - [x] Update `process-kcp/build.gradle.kts` to use `kotlin-compiler`
- [x] Refactor Extension Registration
    - [x] Update `BridgePluginRegistrar.kt` with explicit registration
- [x] Verification
    - [x] Trigger Kotlin Native compilation (Partial verification due to network issues)
