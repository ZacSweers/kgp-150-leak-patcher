# kgp-150-leak-patcher

A simple plugin to automatically patch the memory leak in Kotlin Gradle Plugin 1.5.0 in 
https://youtrack.jetbrains.com/issue/KT-46368.

Implementation based on [David Burström](https://github.com/davidburstrom)'s workaround [here](https://youtrack.jetbrains.com/issue/KT-46368#focus=Comments-27-4868598.0-0).

## Usage

Apply the plugin in the root `build.gradle` file, no other configuration needed. It will no-op if 
the detected runtime version of KGP is not `1.5.0`.

```kotlin
// Apply in the root build.gradle file
plugins {
 id("dev.zacsweers.kgp-150-leak-patcher") version "1.0.0"
}

// Or legacy way
buildscript {
  dependencies {
    classpath("dev.zacsweers:kgp-150-leak-patcher:1.0.0")
  }
}

apply(plugin = "dev.zacsweers.kgp-150-leak-patcher")
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snapshots].

License
-------

    Copyright (C) 2021 Zac Sweers

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[snapshots]: https://oss.sonatype.org/content/repositories/snapshots/