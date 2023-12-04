fun main() {

    data class Segment(val x: Int, val y: Int)

    val n = readln().toInt()
    val a = Array<Segment>(n) { val (x, y) = readln().split(' ').map(String::toInt); Segment(x, y) }

    a.sortBy { it.y }

    var r = 1
    var last = a[0]
    for (i in 1 until n) {
        if (a[i].x >= last.y) {
            last = a[i]
            ++r
        }
    }

    println(r)
}
