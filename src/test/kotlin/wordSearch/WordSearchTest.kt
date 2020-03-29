package wordSearch

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class WordSearchTest {
    @Test
    fun `should create 14x14 character grid`() {
        val grid = createWordSearch(listOf(""))

        grid.map { println(it) }

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
    }

    @Test
    fun `should create grid with a horizontal word included`() {
        val grid = createWordSearch(listOf("TESTLOCATION"))

        grid.map { println(it) }
        val result = grid.flatten().joinToString("")

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
        assertThat(result).contains("TESTLOCATION")
    }

    @Test
    fun `should create grid with 2 horizontal words included`() {
        val grid = createWordSearch(listOf("FIRSTWORD", "SECONDWORD"))

        grid.map { println(it) }
        val result = grid.flatten().joinToString("")

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
        assertThat(result).contains("FIRSTWORD")
        assertThat(result).contains("SECONDWORD")
    }

    @Test
    fun `should create grid with 3 horizontal words included`() {
        val grid = createWordSearch(listOf("FIRSTWORD", "SECONDWORD", "THIRDWORD"))

        grid.map { println(it) }
        val result = grid.flatten().joinToString("")

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
        assertThat(result).contains("FIRSTWORD")
        assertThat(result).contains("SECONDWORD")
        assertThat(result).contains("THIRDWORD")
    }

    @Test
    fun `should create grid with a vertical word included`() {
        val grid = createWordSearch(listOf("VERTICALWORD"), "vertical")

        grid.map { println(it) }

        //transposing again below so we can read word horizontally in test
        val result = grid.transpose().flatten().joinToString("")

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
        assertThat(result).contains("VERTICALWORD")
    }
}
