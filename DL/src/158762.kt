fun main() {
    val n = readln().trim().toInt()
    val a = Array<Int>(n) { readln().trim().toInt() }
    println(a.sortedArray().slice(1 until n - 1).joinToString("\n"))
}
