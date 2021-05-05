package dev.zacsweers.kgp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.util.VersionNumber
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties

/**
 * Cover for a memory leak in KGP 1.5.0 - https://youtrack.jetbrains.com/issue/KT-46368
 *
 * Remove in 1.5.10!
 */
class Kgp150LeakPatcherPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    // implementationVersion is like '1.5.0-release-749 (1.5.0)'
    val embeddableVersion = CompilerSystemProperties::class.java.`package`
      .implementationVersion
      .substringBefore("-")

    if (VersionNumber.parse(embeddableVersion).baseVersion != KGP_150) {
      project.logger.warn("KGP 1.5.0 Leak Patcher plugin is only applicable to Kotlin 1.5.0. Detected version '$embeddableVersion'.")
      return
    }

    project.gradle.buildFinished {
      CompilerSystemProperties.systemPropertyGetter = SystemPropertyGetter()
      CompilerSystemProperties.systemPropertySetter = SystemPropertySetter()
      CompilerSystemProperties.systemPropertyCleaner = SystemPropertyCleaner()
    }
  }
}

private val KGP_150 = VersionNumber.parse("1.5.0")

private class SystemPropertyGetter : (String) -> String? {
  override operator fun invoke(p1: String): String? = System.getProperty(p1)
}

private class SystemPropertySetter : (String, String) -> String? {
  override operator fun invoke(o1: String, o2: String): String = System.setProperty(o1, o2)
}

private class SystemPropertyCleaner : (String) -> String? {
  override operator fun invoke(p1: String): String = System.clearProperty(p1)
}