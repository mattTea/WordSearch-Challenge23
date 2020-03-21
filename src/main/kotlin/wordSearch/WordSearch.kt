package wordSearch

fun createWordSearch(word: String): List<List<Char>> {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXTZ"

    fun createRow() = (1..14).map {
        letters.random()
    }

    val grid = (1..14).map { createRow() }

    fun insertWord(grid: List<List<Char>>, word: String): List<List<Char>> {
        val randomRowNumber = (grid.indices).random()
        val row = grid[randomRowNumber]

        fun intRange(wordLength: Int): IntRange {
            val startInt = (0 until 14 - wordLength).random()
            val endInt = startInt + wordLength - 1
            return IntRange(startInt, endInt)
        }

        val newRow = row.joinToString("").replaceRange(
            range = intRange(word.length),
            replacement = word
        ).toList()

        return grid.mapIndexed { index, existingRow ->
            if (index == randomRowNumber) {
                newRow
            } else {
                existingRow
            }
        }
    }

    return insertWord(grid, word)
}

fun main() {
    createWordSearch("").map { println(it) }
}