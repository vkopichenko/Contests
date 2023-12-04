fun main() {
    val (n, m) = readln().trim().split(' ').map(String::toInt)
    val a = Array<Array<Int>>(n) { Array<Int>(n) { 0 } }
    repeat(m) {
        val (i, j) = readln().trim().split(' ').map { it.toInt() - 1 }
        a[i][j] = 1
        a[j][i] = 1
    }
    for (i in 0 until n) {
        println(a[i].joinToString(" "))
    }
}
