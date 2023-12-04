// https://www.hackerrank.com/contests/projecteuler/challenges/euler001

fun main() {
    val t = readln().toInt()
    repeat(t) {
        val n = readln().toInt()
        val r = (1 until n).asSequence().filter { it % 3 == 0 || it % 5 == 0 }.sum()
        println(r)
    }
}
