Changelog
=========

1.1.0
-----

_2021-05-08_

**New:** The plugin now uses a Gradle `BuildService` under the hood, which makes it 
compatible with Configuration Caching. Thanks to 
[@christiandeange](https://github.com/christiandeange) from Square for contributing this!

**New:** If you need to disable the applicability check for some reason, it can be disabled by 
setting the `kgp15leak.checkApplicability` property to `false`.

1.0.1
-----

_2021-05-05_

**Fix:** Inverted logic on the plugin application in a recent refactoring meant the plugin wasn't getting applied.

Thanks to [@msfjarvis](https://github.com/msfjarvis) for this quick fix!

1.0.0
-----

_2021-05-05_

Initial release.
