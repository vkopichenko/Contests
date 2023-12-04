fun main() {
    val n = readln().toInt()
    val a = readln().split(' ').filterNot(String::isEmpty).map { it.toInt() - 1 }
    val shifts = IntArray(n) { i -> (n + i - a[i]) % n }
//    println(shifts.joinToString(" "))
    val counts = shifts.asList().groupingBy { it }.eachCount()
//    println(counts)
    val k = (0 until n).find { !counts.containsKey(it) }
    println(k ?: -1)
}
