fun main() {

    data class Segment(val x: Int, val y: Int) {
        fun contains(p: Int) = p in x until y
        fun intersects(s: Segment) = contains(s.x) || s.contains(x)
        fun contains(s: Segment) = contains(s.x) && s.y <= y
        override fun toString() = "$x,$y"
    }

    val n = readln().toInt()
    val a = Array<Segment>(n) { val (x, y) = readln().split(' ').map(String::toInt); Segment(x, y) }

//    a.sortBy { it.x }
    a.sortWith(compareBy({ it.x }, { it.y }))
//    println(a.joinToString(" "))

    var r = 0
    var last: Segment? = null
    for (i in 0 until n) {
        if (last != null && last.intersects(a[i])) continue
        if (i < n - 1 && a[i].contains(a[i+1])) continue
        last = a[i]
        ++r
    }

    println(r)
}
