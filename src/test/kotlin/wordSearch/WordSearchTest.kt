package wordSearch

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
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
    fun `should create grid with 2 words included in either horizontal or vertical planes`() {
        val grid = createWordSearch(listOf("FIRSTWORD", "SECONDWORD"))

        grid.map { println(it) }
        val result = checkBothPlanes(grid, "FIRSTWORD", "SECONDWORD")

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
        assertThat(result).isTrue()
    }
}

private fun checkBothPlanes(grid: List<List<Char>>, word1: String, word2: String): Boolean {
    val horizontalCheck = grid.flatten().joinToString("")
    val verticalCheck = grid.transpose().flatten().joinToString("")

    return when {
        horizontalCheck.contains(word1) && horizontalCheck.contains(word2) -> true
        verticalCheck.contains(word1) && verticalCheck.contains(word2) -> true
        horizontalCheck.contains(word1) && verticalCheck.contains(word2) -> true
        horizontalCheck.contains(word2) && verticalCheck.contains(word1) -> true
        else -> false
    }
}