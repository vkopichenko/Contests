fun main() {
    val digits = "one,two,three,four,five,six,seven,eight,nine".split(',') +
            (1..9).map { "$it" }
    fun String.toDigit() = takeIf { it.length == 1 } ?: digits.indexOf(this).plus(1).toString()
    generateSequence { readlnOrNull() }
        .map { listOf(it.findAnyOf(digits), it.findLastAnyOf(digits)) }
        .map { it.joinToString("") { it!!.second.toDigit() }.toInt() }
        .sum().let(::println)
}
