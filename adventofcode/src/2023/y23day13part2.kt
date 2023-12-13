fun main() {
    fun readPattern() = generateSequence { readlnOrNull()?.takeIf { it.isNotEmpty() } }.toList()
    fun List<String>.transpose(): List<String> =
        this[0].indices.map { j -> this.indices.joinToString("") { i -> this[i][j].toString() } }
    infix fun String.diffCount(that: String) = indices.count { this[it] != that[it] }
    fun List<String>.smudgySymmetrical(count: Int): Boolean {
        var smudge = 0
        return (0 until count).all {
            count + it >= size ||
                (get(count - it - 1) diffCount get(count + it))
                    .let { it == 0 || it == 1 && smudge++ == 0 }
        } && smudge == 1
    }
    fun List<String>.countToReflection(): Int =
        indices.run { start + 1 .. endInclusive }.filter { smudgySymmetrical(it) }.sum()
    generateSequence { readPattern().takeIf { it.isNotEmpty() } }.sumOf { pattern ->
        val horizontalCount = pattern.countToReflection()
        val verticalCount = pattern.transpose().countToReflection()
        println("$horizontalCount, $verticalCount")
        100 * horizontalCount + verticalCount
    }.let(::println)
}
