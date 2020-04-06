package wordSearch

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

class WordSearchTest {
    @Test
    fun `should create 14x14 character grid`() {
        val grid = createWordSearch(listOf(""), 0)

        grid.map { println(it) }

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
    }

//    @Test
//    fun `should create grid with 2 words included in either horizontal or vertical planes`() {
//        val grid = createWordSearch(listOf("FIRSTWORD", "SECONDWORD"), 2)
//
//        grid.map { println(it) }
//        val result = checkBothPlanes(grid, "FIRSTWORD", "SECONDWORD")
//
//        assertThat(grid.size).isEqualTo(14)
//        assertThat(grid[0].size).isEqualTo(14)
//        assertThat(grid.flatten().size).isEqualTo(196)
//        assertThat(result).isTrue()
//    }

    @Test
    fun `SECOND ATTEMPT should hide a horizontal word in grid`() {
        val grid = createWordSearchGrid("TESTWORD")

        grid.map { println(it) }

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
        assertThat(grid.flatten().joinToString("")).contains("TESTWORD")
    }

    @Test
    fun `should return position and value of existing character in row`() {
        val grid = listOf(
            listOf('-', '-', 'O'),
            listOf('-', '-', 'N'),
            listOf('-', '-', 'E')
        )

        val result = lettersInPlace(grid, 1)
        assertThat(result).isEqualTo(listOf(Pair(2, 'N')))
    }

    @Test
    fun `should return position and value of 2 existing characters in row`() {
        val grid = listOf(
            listOf('-', 'T', '-', '-'),
            listOf('-', 'W', '-', 'O'),
            listOf('-', 'O', '-', 'N'),
            listOf('-', '-', '-', 'E')
        )

        val result = lettersInPlace(grid, 2)
        assertThat(result).isEqualTo(listOf(Pair(1, 'O'), Pair(3, 'N')))
    }

    @Test
    fun `should return a usable alternative word`() {
        val words = listOf("ONE", "TWO", "BEAN", "BEANS", "DONE", "DOWN")
        val wordsAndRowIndices = listOf(Pair(2, "DONE"))
        val lettersInPlace = listOf(Pair(1, 'O'), Pair(3, 'N'))
        val index = 2

        val result = usableWords(words, wordsAndRowIndices, lettersInPlace, index)

        assertThat(result).containsExactly("DOWN")
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