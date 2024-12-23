fun main() {
    val input = generateSequence { readlnOrNull() }.joinToString() // OMG, it was multiline!

    // part 1
    """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
        .findAll(input).map {
            it.destructured.let { (a, b) -> a.toLong() * b.toLong() }
        }.sum().also(::println)

    // part 2
    var enabled = true
    """mul\((\d{1,3}),(\d{1,3})\)|\Qdo()\E|\Qdon't()\E""".toRegex()
        .findAll(input).filter {
            when (it.groupValues[0]) {
                "do()" -> { enabled = true; false }
                "don't()" -> { enabled = false; false }
                else -> enabled
            }
        }.map {
            it.groupValues.subList(1, 3).map(String::toLong).fold(1L) { a, b -> a * b }
        }.sum().also(::println)
}
