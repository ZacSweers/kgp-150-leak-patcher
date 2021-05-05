package dev.zacsweers.kgp

import com.google.common.truth.Truth.assertThat
import org.gradle.util.VersionNumber
import org.junit.Test

class EmbeddableVersionCheckerTest {

  @Test
  fun simpleTest() {
    val version = Kgp150LeakPatcherPlugin.parseCompilerEmbeddedVersionNumber(
      "1.5.0-release-749 (1.5.0)"
    )
    assertThat(version).isEqualTo(VersionNumber.parse("1.5.0"))
    assertThat(Kgp150LeakPatcherPlugin.isApplicable(version)).isTrue()

    val newVersion = Kgp150LeakPatcherPlugin.parseCompilerEmbeddedVersionNumber(
      "1.5.10-RC-release-749 (1.5.10)"
    )
    assertThat(newVersion).isEqualTo(VersionNumber.parse("1.5.10"))
    assertThat(Kgp150LeakPatcherPlugin.isApplicable(newVersion)).isFalse()

    val oldVersion = Kgp150LeakPatcherPlugin.parseCompilerEmbeddedVersionNumber(
      "1.4.32-RC-release-749 (1.4.32)"
    )
    assertThat(oldVersion).isEqualTo(VersionNumber.parse("1.4.32"))
    assertThat(Kgp150LeakPatcherPlugin.isApplicable(oldVersion)).isFalse()
  }

  @Test
  fun dynamicTest() {
    // Compute based on CI test
    val target = System.getenv().getOrDefault("ci_kotlin_version", "1.5.0")
    val version = Kgp150LeakPatcherPlugin.parseCompilerEmbeddedVersionNumber()
    assertThat(version).isEqualTo(VersionNumber.parse(target))
  }
}