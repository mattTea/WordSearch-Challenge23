package wordSearch

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class WordSearchTest {
    @Test
    fun `should produce 14x14 character grid`() {
        val grid = createWordSearch()

        assertThat(grid.size).isEqualTo(14)
        assertThat(grid[0].size).isEqualTo(14)
        assertThat(grid.flatten().size).isEqualTo(196)
    }
}