package dev.zacsweers.kgp

import com.google.common.truth.Truth.assertThat
import org.gradle.util.VersionNumber
import org.junit.Test

class EmbeddableVersionCheckerTest {

  @Test
  fun simpleTest() {
    val version = parseCompilerEmbeddedVersionNumber(
      "1.5.0-release-749 (1.5.0)"
    )
    assertThat(version).isEqualTo(VersionNumber.parse("1.5.0"))
    assertThat(isApplicable(version)).isTrue()

    val newVersion = parseCompilerEmbeddedVersionNumber(
      "1.5.10-RC-release-749 (1.5.10)"
    )
    assertThat(newVersion).isEqualTo(VersionNumber.parse("1.5.10"))
    assertThat(isApplicable(newVersion)).isFalse()

    val oldVersion = parseCompilerEmbeddedVersionNumber(
      "1.4.32-RC-release-749 (1.4.32)"
    )
    assertThat(oldVersion).isEqualTo(VersionNumber.parse("1.4.32"))
    assertThat(isApplicable(oldVersion)).isFalse()
  }

  @Test
  fun dynamicTest() {
    // Compute based on CI test
    val target = System.getenv().getOrDefault("ci_kotlin_version", "1.5.0")
    val version = parseCompilerEmbeddedVersionNumber()
    assertThat(version).isEqualTo(VersionNumber.parse(target))
  }

  @Test
  fun applicability() {
    assertThat(shouldCheckApplicability("true")).isTrue()
    assertThat(shouldCheckApplicability("false")).isFalse()
    assertThat(shouldCheckApplicability(null)).isTrue()
  }
}