package dev.zacsweers.kgp

import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties

/**
 * By implementing [AutoCloseable], Gradle will call the [close] method at the end of the build.
 */
internal abstract class Kgp150LeakPatcherBuildService : BuildService<BuildServiceParameters.None>, AutoCloseable {

  override fun close() {
    CompilerSystemProperties.systemPropertyGetter = SystemPropertyGetter()
    CompilerSystemProperties.systemPropertySetter = SystemPropertySetter()
    CompilerSystemProperties.systemPropertyCleaner = SystemPropertyCleaner()
  }
}

private class SystemPropertyGetter : (String) -> String? {
  override operator fun invoke(p1: String): String? = System.getProperty(p1)
}

private class SystemPropertySetter : (String, String) -> String? {
  override operator fun invoke(o1: String, o2: String): String = System.setProperty(o1, o2)
}

private class SystemPropertyCleaner : (String) -> String? {
  override operator fun invoke(p1: String): String = System.clearProperty(p1)
}
