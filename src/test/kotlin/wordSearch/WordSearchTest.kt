package wordSearch

import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import kotlin.collections.containsAll

class WordSearchTest {
    @Test
    fun `should create 14x14 character grid`() {
        val grid = createWordSearch(listOf(""), 0)

        grid.map { println(it) }

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
    }

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

        val result = usableWords(words, lettersInPlace, 4)

        assertThat(result).containsExactly("DOWN")
    }

    @Test
    fun `should return horizontal and vertical words already present in grid`() {
        val grid = listOf(
            listOf('-', '-', '-', '-', '-', '-'),
            listOf('-', '-', 'Y', '-', '-', '-'),
            listOf('P', 'L', 'E', 'A', 'S', 'E'),
            listOf('-', '-', 'S', '-', '-', '-'),
            listOf('-', '-', '-', '-', '-', '-'),
            listOf('-', '-', '-', '-', '-', '-')
        )

        val result = wordsInGrid(grid, listOf("YES", "THANKS", "PLEASE", "LOVELY"))

        assertThat(result).containsOnly("YES", "PLEASE")
    }

    @Test
    fun `should create grid with 5 words included in either horizontal or vertical planes`() {
        val grid = createWordSearch(words, 5)

        grid.map { println(it) }
        val result = wordsInGrid(grid, words)
        println(result)

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
        assertThat(result.size).isGreaterThanOrEqualTo(5)
        // greater than or equal to to cover double counting of e.g. JLBRENTCROSS and BRENTCROSS
    }
}