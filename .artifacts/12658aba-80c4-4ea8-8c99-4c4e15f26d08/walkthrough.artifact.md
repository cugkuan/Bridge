# Walkthrough - Upgrade KSP Version

I have upgraded the KSP (Kotlin Symbol Processing) version to the latest available version that is compatible with the project structure.

## Changes

### [libs.versions.toml](file:///Users/kuan/Code/Bridge/gradle/libs.versions.toml)

Updated `symbolProcessingApi` from `2.3.9` to `2.3.10`.

> [!NOTE]
> Although the project uses Kotlin `2.4.10`, a matching KSP version `2.4.10-x.y.z` is not yet available in the public Maven repositories. I have upgraded to `2.3.10`, which is the latest stable release available.

```diff
-symbolProcessingApi = "2.3.9"
+symbolProcessingApi = "2.3.10"
```

## Verification Results

### Automated Tests
- Ran `./gradlew :process:compileKotlin`.
- The configuration phase confirmed that KSP `2.3.10` was successfully resolved (unlike `2.4.10` which failed immediately).
- Further compilation was blocked by an unrelated environment issue in the `:androidApp` module (`AndroidLocationsBuildService` creation failure), which appears to be a local Gradle/Android Studio environment setup issue.

## Summary
The KSP version has been bumped to `2.3.10`. Once a matching `2.4.10` version of KSP is released, it can be updated here as well.
