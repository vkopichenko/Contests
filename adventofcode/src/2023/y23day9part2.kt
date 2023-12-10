fun main() {
    fun List<Int>.extrapolate(): Int =
        if (isEmpty()) 0
        else first() - windowed(2).map { (a, b) -> b - a }.extrapolate()
    generateSequence { readlnOrNull()?.split(' ')?.map { it.toInt() } }
        .map { it.extrapolate() }
        .sum().let(::println)
}
