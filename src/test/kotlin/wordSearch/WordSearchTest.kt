package wordSearch

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class WordSearchTest {
    @Test
    fun `should create 14x14 character grid`() {
        val grid = createWordSearch("")

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
    }

    @Test
    fun `should create grid with a horizontal location word included`() {
        val grid = createWordSearch("TESTLOCATION")

        val result = grid.flatten().joinToString("")

        assertThat(result).contains("TESTLOCATION")
    }
}