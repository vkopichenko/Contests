fun main() {
    val n = readln().toInt()
    val crush = readln().split(' ').map { it.toInt() }
    val visited = Array<Boolean>(n) { false }
    val circles = mutableListOf<Int>()
    for (i in 0 until n) {
        var count = 0
        var p = i
        while (!visited[p]) {
            visited[p] = true
            p = crush[p] - 1
            ++count
        }
        if (p == i) {
            if (count > 1) circles += count
        } else {
            println(-1)
            return
        }
    }

    fun nod(a: Int, b: Int): Int = if (b == 0) a else nod(b, a % b)
    fun nok(a: Int, b: Int) = a / nod(a, b) * b

    var r = when (circles.size) {
        0 -> 1
        1 -> circles[0]
        else -> circles.reduce(::nok)
    }
    if (r % 2 == 0) r /= 2
    println(r)
}
