package wordSearch

fun main() {
    createWordSearch(words, 10).map { println(it) }
//    createWordSearch(listOf("EEEEEEEEEEEEEA", "EEEEEEEEEEEEEB", "EEEEEEEEEEEEEC", "EEEEEEEEEEEEED", "EEEEEEEEEEEEEE", "EEEEEEEEEEEEEF", "EEEEEEEEEEEEEG", "EEEEEEEEEEEEEH", "EEEEEEEEEEEEEI", "EEEEEEEEEEEEEJ", "EEEEEEEEEEEEEK", "EEEEEEEEEEEEEL", "EEEEEEEEEEEEEM", "EEEEEEEEEEEEEN", "EEEEEEEEEEEEEO", "EEEEEEEEEEEEEP", "EEEEEEEEEEEEEQ", "EEEEEEEEEEEEER", "EEEEEEEEEEEEES", "EEEEEEEEEEEEET", "EEEEEEEEEEEEEU", "EEEEEEEEEEEEEV", "EEEEEEEEEEEEEW", "EEEEEEEEEEEEEX", "EEEEEEEEEEEEEY", "EEEEEEEEEEEEEZ"), 4).map { println(it) }
//    createWordSearch(listOf("FIRSTWORD", "SECONDWORD"), 2).map { println(it) }
}

fun createWordSearch(words: List<String>, wordsToHide: Int): List<List<Char>> {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun createRow() = (1..14).map {
//        letters.random()
        '-'
    }

    val grid = (1..14).map { createRow() }

    fun insertWords(grid: List<List<Char>>, words: List<String>): List<List<Char>> {

        val wordsAndRowIndexesToHideThemIn =
            grid.indices
                .shuffled()
                .take(wordsToHide)
                .mapIndexed { index, rowNumber -> Pair(rowNumber, words[index]) }

        fun rangeInRowToBeReplaced(wordLength: Int): IntRange {
            val startInt = (0..14 - wordLength).random()
            val endInt = startInt + wordLength - 1
            return IntRange(startInt, endInt)
        }

        fun createNewRowWithHiddenWord(grid: List<List<Char>>, rowNumber: Int, word: String): List<Char> {
            return grid[rowNumber]
                .joinToString("")
                .replaceRange(
                    range = rangeInRowToBeReplaced(word.length),
                    replacement = word
                )
                .toList()
        }

        val horizontalWordsAndRowIndexesToHideThemIn = wordsAndRowIndexesToHideThemIn
            .take((0..wordsToHide).random())

        val gridWithHorizontalHiddenWords = grid.mapIndexed { index, existingRow ->
            if (horizontalWordsAndRowIndexesToHideThemIn.any { it.first == index }) {
                createNewRowWithHiddenWord(
                    grid = grid,
                    rowNumber = index,
                    word = horizontalWordsAndRowIndexesToHideThemIn.single { it.first == index }.second
                )
            } else {
                existingRow
            }
        }

        val verticalWordsAndRowIndexesToHideThemIn =
            wordsAndRowIndexesToHideThemIn - horizontalWordsAndRowIndexesToHideThemIn

        val transposedGridWithHorizontalHiddenWords = gridWithHorizontalHiddenWords.transpose()

        return transposedGridWithHorizontalHiddenWords.mapIndexed { index, existingRow ->
            if (verticalWordsAndRowIndexesToHideThemIn.any { it.first == index }) {

                val originalWordLength = verticalWordsAndRowIndexesToHideThemIn.single { it.first == index }.second.length

                val lettersAlreadyInRow = transposedGridWithHorizontalHiddenWords[index]
                    .filterNot { it == '-' }
                    .isNotEmpty()

                if (!lettersAlreadyInRow) {
                    createNewRowWithHiddenWord(
                        grid = transposedGridWithHorizontalHiddenWords,
                        rowNumber = index,
                        word = verticalWordsAndRowIndexesToHideThemIn.single { it.first == index }.second
                    )
                } else {
                    // find letters in place and their indexes
                    val lettersInPlace = lettersInPlace(transposedGridWithHorizontalHiddenWords, index)

                    // find words in list that have letters in these indexes
                    val usableWords = usableWords(words, lettersInPlace, originalWordLength)

                    if (usableWords.isNotEmpty()) {
                        val wordsAlreadyInGrid = wordsInGrid(transposedGridWithHorizontalHiddenWords, words)
                        fun nextUsableWord(): String {
                            val availableUsableWords =
                                usableWords - wordsAndRowIndexesToHideThemIn.map { it.second } - wordsAlreadyInGrid
                            return if (availableUsableWords.isNotEmpty()) availableUsableWords.first() else "*"
                        }
                        createNewRowWithHiddenWord(
                            grid = transposedGridWithHorizontalHiddenWords,
                            rowNumber = index,
                            word = nextUsableWord()
                        )
                    } else {
                        // else pick a different row and create a New Hidden Row and see if that fits
                        val alternativeRows = transposedGridWithHorizontalHiddenWords.mapIndexedNotNull { altIndex, _ ->
                            val lettersInCurrentRow = lettersInPlace(transposedGridWithHorizontalHiddenWords, altIndex)
                            val alternativeWords =
                                usableWords(words, lettersInCurrentRow, originalWordLength)

                            if (alternativeWords.isNotEmpty()) Pair(altIndex, alternativeWords.first()) else null
                        }

                        if (alternativeRows.isNotEmpty()) {
                            createNewRowWithHiddenWord(
                                grid = transposedGridWithHorizontalHiddenWords,
                                rowNumber = alternativeRows.first().first,
                                word = alternativeRows.first().second
                            )
                        } else {
                            existingRow
                        }
                    }

                    // if no matches start grid again
                }
            } else {
                existingRow
//                createNewRowWithHiddenWord(index, "yyyyyyyyyyyyyy")
            }
        }
    }

    return insertWords(grid, words)
}

fun usableWords(
    words: List<String>,
    lettersInRow: List<Pair<Int, Char>>,
    wordLength: Int
): List<String> {
    return words.mapNotNull { word ->
        // for every word in words check whether it has the letters in correct place as per list of lettersInRow
        if (lettersInRow.all { (word.length - 1 >= it.first && word[it.first] == it.second) }) word else null
        // then filter this list for word of the right length
    }.filter { it.length == wordLength }
}

fun lettersInPlace(grid: List<List<Char>>, index: Int): List<Pair<Int, Char>> = grid[index]
    .mapIndexed { letterIndex, letter ->
        if (letter != '-') Pair(letterIndex, letter) else null
    }.filterNotNull()


fun wordsInGrid(grid: List<List<Char>>, words: List<String>): List<String> {
    val horizontalString = grid.flatten().joinToString("")
    val verticalString = grid.transpose().flatten().joinToString("")

    val flatGridInBothPlanes = (horizontalString + verticalString)

    return words.mapNotNull { if (flatGridInBothPlanes.contains(it)) it else null }
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