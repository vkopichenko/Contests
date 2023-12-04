fun main() {
    val (n, m) = readln().split(' ').map(String::toInt)
    val a = Array(n) { readln().asSequence().drop(1).take(m).map { it == '1' }.toList()/*.toBooleanArray()*/ }
    val targetFloor = a.indexOfFirst { it.any { it } }
    if (targetFloor == -1) {
        println(0)
    } else {
        val best = Array(n + 1) { IntArray(2) }
        best[n][0] = 0
        best[n][1] = Int.MAX_VALUE shr 1
        for (i in n - 1 downTo targetFloor) {
            best[i][0] = 1 + Math.min(
                    best[i + 1][0] + 2 * (a[i].lastIndexOf(true) + 1),
                    best[i + 1][1] + m + 1)
            best[i][1] = 1 + Math.min(
                    best[i + 1][1] + 2 * (a[i].asReversed().lastIndexOf(true) + 1),
                    best[i + 1][0] + m + 1)
        }
        val i = targetFloor
        println(Math.min(
                best[i + 1][0] + (a[i].lastIndexOf(true) + 1),
                best[i + 1][1] + (a[i].asReversed().lastIndexOf(true) + 1))
        )
    }
}
