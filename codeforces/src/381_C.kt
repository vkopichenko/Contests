fun main() {
    val (n, m) = readln().split(' ').map { it.toInt() }
    val lr = Array(m, { readln().split(' ').map { it.toInt() }.run { Pair(this[0], this[1]) } })
    val mex = lr.map { it.second - it.first }.min()!! + 1
    println(mex)
    for (i in 0 until n) print("${i % mex} ")
}
