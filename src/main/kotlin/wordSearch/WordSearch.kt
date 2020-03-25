package wordSearch

fun createWordSearch(words: List<String>): List<List<Char>> {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXTZ"

    fun createRow() = (1..14).map {
        letters.random()
    }

    val grid = (1..14).map { createRow() }

    fun insertWords(grid: List<List<Char>>, words: List<String>): List<List<Char>> {
        val randomRowNumbers = grid.indices.shuffled().take(words.size)
        val wordsAndRowsToHideThemIn = randomRowNumbers.mapIndexed { index, rowNumber ->
            Pair(rowNumber, words[index])
        }

        fun rangeInRowToBeReplaced(wordLength: Int): IntRange {
            val startInt = (0 until 14 - wordLength).random()
            val endInt = startInt + wordLength - 1
            return IntRange(startInt, endInt)
        }

        fun createNewRowWithHiddenWord(rowNumber: Int, word: String): List<Char> {
            val row = grid[rowNumber]

            return row.joinToString("").replaceRange(
                range = rangeInRowToBeReplaced(word.length),
                replacement = word
            ).toList()
        }

        return grid.mapIndexed { index, existingRow ->
            wordsAndRowsToHideThemIn.flatMap {
                if (index == it.first) {
                    createNewRowWithHiddenWord(it.first, it.second)
                } else {
                    existingRow
                }
            }

        }
    }

    return insertWords(grid, words)
}

fun main() {
    createWordSearch(listOf("A", "B", "C", "D", "E", "F", "A", "B", "C", "D", "E", "F")).map { println(it) }
    println()
}