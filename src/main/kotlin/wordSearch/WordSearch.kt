package wordSearch

fun createWordSearch(words: List<String>, plane: String = "row"): List<List<Char>> {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun createRow() = (1..14).map {
        letters.random()
    }

    val grid = (1..14).map { createRow() }

    fun insertWords(grid: List<List<Char>>, words: List<String>): List<List<Char>> {
        fun createStartingGrid(): List<List<Char>> {
            return when (plane) {
                "vertical" -> grid.transpose()
                else -> grid
            }
        }

        val startingGrid = createStartingGrid()

        val wordsAndRowsToHideThemIn =
            startingGrid.indices
                .shuffled()
                .take(words.size)
                .mapIndexed { index, rowNumber -> Pair(rowNumber, words[index]) }

        fun rangeInRowToBeReplaced(wordLength: Int): IntRange {
            val startInt = (0 until 14 - wordLength).random()
            val endInt = startInt + wordLength - 1
            return IntRange(startInt, endInt)
        }

        fun createNewRowWithHiddenWord(rowNumber: Int, word: String): List<Char> {
            return startingGrid[rowNumber]
                .joinToString("")
                .replaceRange(
                    range = rangeInRowToBeReplaced(word.length),
                    replacement = word
                )
                .toList()
        }

        val gridWithHiddenWords = startingGrid.mapIndexed { index, existingRow ->
             if (wordsAndRowsToHideThemIn.any { it.first == index }) {
                 createNewRowWithHiddenWord(index, wordsAndRowsToHideThemIn.single { it.first == index }.second)
             } else {
                 existingRow
             }
        }

        return when (plane) {
            "vertical" -> gridWithHiddenWords.transpose()
            else -> gridWithHiddenWords
        }
    }

    return insertWords(grid, words)
}

fun main() {
    createWordSearch(listOf("A", "B", "C", "D", "E", "F", "A", "B", "C", "D", "E", "F")).map { println(it) }
    println()
}

fun <E> List<List<E>>.transpose(): List<List<E>> {
    val width = first().size

    return (0 until width).map { col ->
        (0 until size).map { row -> this[row][col] }
    }
}

//fun main() {
//    val grid = listOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9))
//
//    println(grid.map { println(it) })
//    // [1, 2, 3]
//    // [4, 5, 6]
//    // [7, 8, 9]
//
//    println("----------")
//
//    println(grid.transpose().map { println(it) })
//    // [1, 4, 7]
//    // [2, 5, 8]
//    // [3, 6, 9]
//}