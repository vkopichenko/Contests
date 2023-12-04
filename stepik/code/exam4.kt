fun main() {
    val n = readln().toInt()
    val a = IntArray(n) { readln().toInt() }
    println(a.asSequence().filter { it % 2 == 0 }.sum())
}
