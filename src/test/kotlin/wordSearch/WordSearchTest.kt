package wordSearch

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class WordSearchTest {
    @Test
    fun `should create 14x14 character grid`() {
        val grid = createWordSearch(listOf(""))

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
    }

    @Test
    fun `should create grid with a horizontal location word included`() {
        val grid = createWordSearch(listOf("TESTLOCATION"))

        val result = grid.flatten().joinToString("")

        assertThat(result).contains("TESTLOCATION")
    }

    @Test
    fun `should create grid with 2 horizontal location words included`() {
        val grid = createWordSearch(listOf("FIRSTWORD", "SECONDWORD"))

        val result = grid.flatten().joinToString("")

        assertThat(result).contains("FIRSTWORD")
        assertThat(result).contains("SECONDWORD")
    }

    @Test
    fun `should create grid with 3 horizontal location words included`() {
        val grid = createWordSearch(listOf("FIRSTWORD", "SECONDWORD", "THIRDWORD"))

        val result = grid.flatten().joinToString("")

        assertThat(result).contains("FIRSTWORD")
        assertThat(result).contains("SECONDWORD")
        assertThat(result).contains("THIRDWORD")
    }
}