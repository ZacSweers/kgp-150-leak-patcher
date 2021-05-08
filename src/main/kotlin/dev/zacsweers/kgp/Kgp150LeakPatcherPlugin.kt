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
    if (shouldCheckApplicability(project.findProperty(APPLICABILITY_CHECK_PROP))) {
      val embeddableVersion = parseCompilerEmbeddedVersionNumber()
      if (!isApplicable(embeddableVersion)) {
        project.logger.warn("KGP 1.5.0 Leak Patcher plugin is only applicable to Kotlin 1.5.0. Detected version '$embeddableVersion'.")
        return
      }
    }

    project.gradle.sharedServices.registerIfAbsent("kgp-150-leak-patcher", Kgp150LeakPatcherBuildService::class.java) {}
      // Force the BuildService to be loaded into memory immediately.
      .get()
  }

  companion object {
    /** A boolean gradle property to disable applicability checks. Use at your own risk! */
    const val APPLICABILITY_CHECK_PROP = "kgp15leak.checkApplicability"
  }
}

private val KGP_150 = VersionNumber.parse("1.5.0")

internal fun shouldCheckApplicability(propValue: Any?): Boolean {
  return propValue?.toString()
    ?.toBoolean()
    ?: true
}

internal fun isApplicable(versionNumber: VersionNumber = parseCompilerEmbeddedVersionNumber()): Boolean {
  return versionNumber == KGP_150
}

private fun parseCompilerEmbeddedVersion(): String? {
  // implementationVersion is like '1.5.0-release-749 (1.5.0)'
  return try {
    CompilerSystemProperties::class.java.`package`.implementationVersion
  } catch (e: NoClassDefFoundError) {
    // Cover for old plugins
    return null
  }
}

internal fun parseCompilerEmbeddedVersionNumber(
  version: String? = parseCompilerEmbeddedVersion()
): VersionNumber {
  return version?.substringBefore("-")
    ?.let(VersionNumber::parse)
    ?.baseVersion
    ?: VersionNumber.UNKNOWN
}
