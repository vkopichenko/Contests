fun main() {
    val fib = generateSequence(1 to 1) { it.second to it.first + it.second }.map { it.first }
    println(fib.take(33).joinToString())
    val evens = fib.takeWhile { it <= 4_000_000 }.filter { it and 1 == 0 }
    println(evens.joinToString())
    println(evens.sum())
}
