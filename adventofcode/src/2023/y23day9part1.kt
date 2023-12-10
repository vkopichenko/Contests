fun main() {
    fun List<Int>.extrapolate(): Int =
        if (isEmpty()) 0
        else last() + asSequence().windowed(2).map { (a, b) -> b - a }.toList().extrapolate()
    generateSequence { readlnOrNull()?.split(' ')?.map { it.toInt() } }
        .map { it.extrapolate() }
        .sum().let(::println)
}
