fun main() {
    fun readPattern() = generateSequence { readlnOrNull()?.takeIf { it.isNotEmpty() } }.toList()
    fun List<String>.transpose(): List<String> =
        this[0].indices.map { j -> this.indices.joinToString("") { i -> this[i][j].toString() } }
    fun <T> List<T>.countToReflection(): Int =
        indices.run { start + 1 .. endInclusive }.filter { count ->
            (0 until count).all { count + it >= size || this[count - it - 1] == this[count + it] }
        }.sum()
    generateSequence { readPattern().takeIf { it.isNotEmpty() } }.sumOf { pattern ->
        val horizontalCount = pattern.countToReflection()
        val verticalCount = pattern.transpose().countToReflection()
        println("$horizontalCount, $verticalCount")
        100 * horizontalCount + verticalCount
    }.let(::println)
}
