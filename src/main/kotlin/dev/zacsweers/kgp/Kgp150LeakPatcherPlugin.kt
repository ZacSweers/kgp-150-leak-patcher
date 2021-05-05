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
    // TODO this isn't reliable
//    if (VersionNumber.parse(KotlinVersion.CURRENT.toString()).baseVersion != KGP_150) {
//      project.logger.lifecycle("KGP 1.5.0 Leak Patcher plugin is only applicable to Kotlin 1.5.0. Detected version ${KotlinVersion.CURRENT}")
//      return
//    }
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