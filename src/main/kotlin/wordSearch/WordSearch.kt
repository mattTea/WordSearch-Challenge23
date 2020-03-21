package wordSearch

fun createWordSearch(): List<List<Char>> {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZ"

    fun createRow() = (1..14).map {
        allowedChars.random()
    }

    return (1..14).map { createRow() }
}

fun main() {
    createWordSearch().map { println(it) }
}