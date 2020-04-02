package wordSearch

fun createWordSearchGrid(word: String): List<List<Char>> {
    val grid = (1..14).map { (1..14).map { '-' } }

    fun startingXCoordinate(word: String): Int {
        return (0..14 - word.length).random()
    }

    fun startingYCoordinate(): Int {
        return grid.indices.random()
    }

    fun gridBuilder(): List<List<Char>> {
        val x = startingXCoordinate(word)
        val y = startingYCoordinate()

        return grid
            .replace(xIndex = x + 0, yIndex = y, letter = word[0 + 0])
            .replace(xIndex = x + 1, yIndex = y, letter = word[0 + 1])
            .replace(xIndex = x + 2, yIndex = y, letter = word[0 + 2])
            .replace(xIndex = x + 3, yIndex = y, letter = word[0 + 3])
            .replace(xIndex = x + 4, yIndex = y, letter = word[0 + 4])
            .replace(xIndex = x + 5, yIndex = y, letter = word[0 + 5])
            .replace(xIndex = x + 6, yIndex = y, letter = word[0 + 6])
            .replace(xIndex = x + 7, yIndex = y, letter = word[0 + 7])
    }

    return gridBuilder()
}

private fun <E> List<List<E>>.replace(xIndex: Int, yIndex: Int, letter: E): List<List<E>> {
    return this.mapIndexed { y, _ ->
        mapIndexed { x, _ ->
            if (y == yIndex && x == xIndex) {
                letter
            } else {
                this[y][x]
            }
        }
    }
}


