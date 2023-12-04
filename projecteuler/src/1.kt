// https://projecteuler.net/problem=1

fun main() {
    val r = (1 until 1000).asSequence().filter { it % 3 == 0 || it % 5 == 0 }.sum()
    println(r)
}
