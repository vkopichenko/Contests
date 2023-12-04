fun main() {
    val n = readln().toInt()
    val a = readln().split(' ').map { it.toInt() }
    assert(n == a.size)
    val min = a.min()!!
    val max = a.max()!!
    val allowed = mutableListOf(min, max)
    val sum = max + min
    if (sum and 1 == 0) allowed += sum / 2
    println(if (a.all { it in allowed }) "YES" else "NO")
}
